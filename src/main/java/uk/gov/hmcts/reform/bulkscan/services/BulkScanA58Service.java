package uk.gov.hmcts.reform.bulkscan.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.bulkscan.config.BulkScanFormValidationConfigManager;
import uk.gov.hmcts.reform.bulkscan.config.BulkScanTransformConfigManager;
import uk.gov.hmcts.reform.bulkscan.group.creation.Group;
import uk.gov.hmcts.reform.bulkscan.group.creation.GroupCreator;
import uk.gov.hmcts.reform.bulkscan.group.handler.BulkScanGroupHandler;
import uk.gov.hmcts.reform.bulkscan.group.util.BulkScanGroupValidatorUtil;
import uk.gov.hmcts.reform.bulkscan.group.validation.enums.MessageTypeEnum;
import uk.gov.hmcts.reform.bulkscan.helper.BulkScanTransformHelper;
import uk.gov.hmcts.reform.bulkscan.helper.BulkScanValidationHelper;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationRequest;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationResponse;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanValidationRequest;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanValidationResponse;
import uk.gov.hmcts.reform.bulkscan.model.CaseCreationDetails;
import uk.gov.hmcts.reform.bulkscan.model.Errors;
import uk.gov.hmcts.reform.bulkscan.model.FormType;
import uk.gov.hmcts.reform.bulkscan.model.OcrDataField;
import uk.gov.hmcts.reform.bulkscan.model.Status;
import uk.gov.hmcts.reform.bulkscan.model.Warnings;
import uk.gov.hmcts.reform.bulkscan.helper.BulkScanConditionalTransformerHelper;

import java.util.*;
import java.util.stream.Collectors;

import static com.microsoft.applicationinsights.core.dependencies.apachecommons.lang3.StringUtils.isNotEmpty;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.FALSE;
import static org.apache.commons.lang3.BooleanUtils.TRUE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.ADOPTION_ORDER_CONSENT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.ADOPTION_ORDER_CONSENT_ADVANCE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.ADOPTION_ORDER_CONSENT_AGENCY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.ADOPTION_ORDER_NO_CONSENT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT1_RELATION_TO_CHILD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_FIRSTNAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_LEGAL_REP_SIGNATURE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_LEGAL_REP_SIGNING;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_LEGAL_REP_SOT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_RELATION_TO_CHILD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_SIGNING;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_SOT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_SOT_DAY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_SOT_MONTH;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT2_SOT_YEAR;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT_RELATION_TO_CHILD_FATHER_PARTNER;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.BULK_SCAN_CASE_REFERENCE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.CASE_TYPE_ID;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.COURT_CONSENT_CHILD_WELFARE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.COURT_CONSENT_PARENT_LACK_CAPACITY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.COURT_CONSENT_PARENT_NOT_FOUND;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.EVENT_ID;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.UNKNOWN_FIELDS_MESSAGE;
import static uk.gov.hmcts.reform.bulkscan.helper.BulkScanTransformHelper.transformScanDocuments;
import static uk.gov.hmcts.reform.bulkscan.model.FormType.A58;
import static uk.gov.hmcts.reform.bulkscan.model.FormType.A58_RELINQUISHED_ADOPTION;
import static uk.gov.hmcts.reform.bulkscan.model.FormType.A58_STEP_PARENT;
import static uk.gov.hmcts.reform.bulkscan.model.Status.ERRORS;
import static uk.gov.hmcts.reform.bulkscan.utils.BulkScanValidationUtil.flattenLists;
import static uk.gov.hmcts.reform.bulkscan.utils.BulkScanValidationUtil.notNull;

@Service
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.GodClass"})
public class BulkScanA58Service implements BulkScanService {

    public static final String STEP_PARENT_ADOPTION = "Step Parent";

    public static final String SCAN_DOCUMENTS = "scannedDocuments";
    public static final String OTHER_PARENT_RELATIONSHIP_TO_CHILD = "otherParentRelationshipToChild";
    public static final String RELATIONSHIP_FATHER = "child_relationship_father";
    public static final String RELATIONSHIP_OTHER = "child_relationship_other";
    public static final String FATHER = "Father";
    public static final String OTHER_PARENT = "Other parent";
    public static final String APPLICANTS_DOMICILE_STATUS = "applicantsDomicileStatus";
    public static final String APPLICANT_RELATION_TO_CHILD = "applicantRelationToChild";
    public static final String APPLICANT_MARITAL_STATUS = "applicantMaritalStatus";

    @Autowired
    BulkScanFormValidationConfigManager configManager;

    @Autowired
    BulkScanValidationHelper bulkScanValidationHelper;

    @Autowired
    BulkScanTransformConfigManager transformConfigManager;

    @Autowired
    BulkScanGroupHandler bulkScanGroupHandler;

    @Autowired
    BulkScanConditionalTransformerHelper bulkScanConditionalTransformerHelper;

    @Override
    public FormType getCaseType() {
        return A58;
    }

    @Override
    public BulkScanValidationResponse validate(BulkScanValidationRequest bulkRequest) {
        FormType formType = A58;

        List<OcrDataField> inputFieldsList = bulkRequest.getOcrdatafields();

        Map<String, String> inputFieldsMap = getOcrDataFieldAsMap(inputFieldsList);

        if (isA58ParentFormType(getOcrDataFieldAsMap(bulkRequest.getOcrdatafields()))) {
            formType = A58_STEP_PARENT;
        } else if (isA58RelinquishedAdoptionFormType(inputFieldsMap)) {
            formType = A58_RELINQUISHED_ADOPTION;
        }
        // Validating the Fields..
        BulkScanValidationResponse bulkScanValidationResponse =
                bulkScanValidationHelper.validateMandatoryAndOptionalFields(bulkRequest.getOcrdatafields(),
                configManager.getValidationConfig(formType));

        if (formType.equals(A58) && isNotEmpty(inputFieldsMap.get(APPLICANT2_FIRSTNAME))) {
            List<String> errorList = getApplicant2ErrorList(inputFieldsMap, bulkScanValidationResponse);
            return bulkScanValidationResponse.toBuilder()
                    .status(!errorList.isEmpty() ? ERRORS : bulkScanValidationResponse.getStatus())
                    .errors(Errors.builder().items(errorList).build()).build();
        }

        updateGroupErrorsAndWarnings(bulkRequest, formType, bulkScanValidationResponse);
        updateGroupMissingFields(bulkScanValidationResponse, formType);
        return bulkScanValidationResponse;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BulkScanTransformationResponse transform(BulkScanTransformationRequest bulkScanTransformationRequest) {
        Map<String, Object> caseData = new HashMap<>();
        List<OcrDataField> inputFieldsList = bulkScanTransformationRequest.getOcrdatafields();

        FormType formType = A58;

        caseData.put(BULK_SCAN_CASE_REFERENCE, bulkScanTransformationRequest.getId());

        Map<String, String> inputFieldsMap = getOcrDataFieldAsMap(inputFieldsList);

        if (isA58ParentFormType(inputFieldsMap)) {
            formType = A58_STEP_PARENT;
        } else if (isA58RelinquishedAdoptionFormType(inputFieldsMap)) {
            formType = A58_RELINQUISHED_ADOPTION;
        }
        List<String> unknownFieldsList = null;

        BulkScanFormValidationConfigManager
                .ValidationConfig validationConfig = configManager.getValidationConfig(formType);

        Map<String, Object> populatedMap = (Map<String, Object>) BulkScanTransformHelper
                .transformToCaseData(new HashMap<>(transformConfigManager
                        .getTransformationConfig(formType).getCaseDataFields()), inputFieldsMap);

        // For A58 formtype we need to set some fields based on the Or Condition...
        if (formType.equals(A58)) {
            bulkScanConditionalTransformerHelper.transform(inputFieldsMap, populatedMap);
        }

        populatedMap.put(SCAN_DOCUMENTS, transformScanDocuments(bulkScanTransformationRequest));

        Map<String, String> caseTypeAndEventId =
                transformConfigManager.getTransformationConfig(formType).getCaseFields();

        BulkScanTransformationResponse.BulkScanTransformationResponseBuilder builder = BulkScanTransformationResponse
                .builder().caseCreationDetails(
                        CaseCreationDetails.builder()
                                .caseTypeId(caseTypeAndEventId.get(CASE_TYPE_ID))
                                .eventId(caseTypeAndEventId.get(EVENT_ID))
                                .caseData(populatedMap).build());

        if (nonNull(validationConfig)) {
            unknownFieldsList = bulkScanValidationHelper
                .findUnknownFields(inputFieldsList,
                                   validationConfig.getMandatoryFields(),
                                   validationConfig.getOptionalFields()
                );
        }
        updateTransformationUnknownFields(formType, unknownFieldsList, builder);
        return builder.build();
    }

    private void updateTransformationUnknownFields(FormType formType, List<String> fieldsNotKnownByYamlBasedImplementation, BulkScanTransformationResponse.BulkScanTransformationResponseBuilder builder) {
        if (null != fieldsNotKnownByYamlBasedImplementation && !fieldsNotKnownByYamlBasedImplementation.isEmpty()) {
            GroupCreator groupCreator = new GroupCreator();
            Optional<Group> groupOptional = Optional.ofNullable(groupCreator.getGroup(formType));
            if(groupOptional.isPresent()) {
                List<String> fieldsKnownByGroupBasedImplementation = BulkScanGroupValidatorUtil.getAllConfiguredGroupFields(
                    groupOptional.get());
                List<String> warningMessages = fieldsNotKnownByYamlBasedImplementation.stream()
                    .filter(warning -> !fieldsKnownByGroupBasedImplementation.contains(warning))
                    .collect(Collectors.toList());
                if(!warningMessages.isEmpty()) {
                    builder.warnings(Arrays.asList(String.format(
                        UNKNOWN_FIELDS_MESSAGE,
                        String.join(",", warningMessages)
                    )));
                }
            } else {
                builder.warnings(Arrays.asList(String.format(
                    UNKNOWN_FIELDS_MESSAGE,
                    String.join(",", fieldsNotKnownByYamlBasedImplementation)
                )));
            }
        }
    }

    private boolean isA58RelinquishedAdoptionFormType(Map<String, String> inputFieldsMap) {
        return nonNull(inputFieldsMap.get(ADOPTION_ORDER_CONSENT))
                || nonNull(inputFieldsMap.get(ADOPTION_ORDER_CONSENT_ADVANCE))
                || nonNull(inputFieldsMap.get(ADOPTION_ORDER_CONSENT_AGENCY))
                || nonNull(inputFieldsMap.get(ADOPTION_ORDER_NO_CONSENT))
                || nonNull(inputFieldsMap.get(COURT_CONSENT_PARENT_NOT_FOUND))
                || nonNull(inputFieldsMap.get(COURT_CONSENT_PARENT_LACK_CAPACITY))
                || nonNull(inputFieldsMap.get(COURT_CONSENT_CHILD_WELFARE));
    }

    private boolean isA58ParentFormType(Map<String, String> inputFieldsMap) {
        return STEP_PARENT_ADOPTION.equalsIgnoreCase(inputFieldsMap.get(APPLICANT1_RELATION_TO_CHILD))
                || STEP_PARENT_ADOPTION.equalsIgnoreCase(inputFieldsMap.get(APPLICANT2_RELATION_TO_CHILD))
                || TRUE.equalsIgnoreCase(inputFieldsMap.get(APPLICANT_RELATION_TO_CHILD_FATHER_PARTNER))
                || FALSE.equalsIgnoreCase(inputFieldsMap.get(APPLICANT_RELATION_TO_CHILD_FATHER_PARTNER));
    }

    private List<String> getApplicant2ErrorList(Map<String, String> inputFieldsMap,
                                                BulkScanValidationResponse bulkScanValidationResponse) {
        return flattenLists(
                bulkScanValidationResponse.getErrors().getItems(),
                notNull(inputFieldsMap.get(APPLICANT2_SOT), APPLICANT2_SOT),
                notNull(inputFieldsMap.get(APPLICANT2_LEGAL_REP_SOT), APPLICANT2_LEGAL_REP_SOT),
                notNull(inputFieldsMap.get(APPLICANT2_LEGAL_REP_SIGNATURE), APPLICANT2_LEGAL_REP_SIGNATURE),
                notNull(inputFieldsMap.get(APPLICANT2_SIGNING), APPLICANT2_SIGNING),
                notNull(inputFieldsMap.get(APPLICANT2_LEGAL_REP_SIGNING), APPLICANT2_LEGAL_REP_SIGNING),
                notNull(inputFieldsMap.get(APPLICANT2_SOT_DAY), APPLICANT2_SOT_DAY),
                notNull(inputFieldsMap.get(APPLICANT2_SOT_MONTH), APPLICANT2_SOT_MONTH),
                notNull(inputFieldsMap.get(APPLICANT2_SOT_YEAR), APPLICANT2_SOT_YEAR)
        );
    }

    /**
     * The yaml based framework validation throws warning for missing fields which are configured in the group
     * validation framework.so this function removes the missing fields from warning message,
     * which are configured in group framework.
     */
    private void updateGroupErrorsAndWarnings(BulkScanValidationRequest bulkRequest, FormType formType,
                                              BulkScanValidationResponse bulkScanValidationResponse) {
        Errors errors = bulkScanValidationResponse.getErrors();
        Warnings warnings = bulkScanValidationResponse.getWarnings();
        List<String> errorsItems = errors.getItems();
        List<String> warningsItems = warnings.getItems();

        Map<MessageTypeEnum, List<String>> errorsAndWarningsHashMap = bulkScanGroupHandler.handle(
            formType,
            bulkRequest.getOcrdatafields()
        );
        errorsItems.addAll(errorsAndWarningsHashMap.get(MessageTypeEnum.ERROR));
        warningsItems.addAll(errorsAndWarningsHashMap.get(MessageTypeEnum.WARNING));

        errors.setItems(errorsItems);
        warnings.setItems(warningsItems);

        bulkScanValidationResponse.setErrors(errors);
        bulkScanValidationResponse.setWarnings(warnings);
    }

    private void updateGroupMissingFields(BulkScanValidationResponse bulkScanValidationResponse, FormType formType) {
        GroupCreator groupCreator = new GroupCreator();
        Group group = groupCreator.getGroup(formType);
        List<String> allConfiguredGroupFields = BulkScanGroupValidatorUtil.getAllConfiguredGroupFields(group);
        List<String> updateWarningList = bulkScanValidationResponse.getWarnings().getItems().stream()
            .map(item -> updateMissingField(item, allConfiguredGroupFields))
            .filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toList());
        if (!updateWarningList.isEmpty()) {
            bulkScanValidationResponse.setWarnings(Warnings.builder().items(updateWarningList).build());
            bulkScanValidationResponse.setStatus(Status.WARNINGS);
        } else {
            bulkScanValidationResponse.setWarnings(Warnings.builder().items(updateWarningList).build());
        }
        if (!bulkScanValidationResponse.getErrors().getItems().isEmpty()) {
            bulkScanValidationResponse.setStatus(ERRORS);
        }
        if (updateWarningList.isEmpty()
            && bulkScanValidationResponse.getErrors().getItems().isEmpty()) {
            bulkScanValidationResponse.setStatus(Status.SUCCESS);
        }
    }

    private String updateMissingField(String item, List<String> allConfiguredGroupFields) {
        if (item.contains("The following fields are are not configured with our system")) {
            List<String> missingFieldList = Arrays.asList(item.split("\\[")[1].split("\\]")[0].split(","));
            String warnings = missingFieldList.stream()
                .filter(s -> !allConfiguredGroupFields.contains(s))
                .collect(Collectors.joining(","));
            if (StringUtils.isEmpty(warnings)) {
                return null;
            } else {
                return "The following fields are are not configured with our system: "
                    + warnings;
            }
        }
        return item;
    }
}
