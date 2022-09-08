package uk.gov.hmcts.reform.bulkscan.services;

import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT_RESPONDENT_RELATIONSHIP_FIELDS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT_RESPONDENT_RELATIONSHIP_NONE_ABOVE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.APPLICANT_RESPONDENT_RELATIONSHIP_OPTIONS_FIELDS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.SCAN_DOCUMENTS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.TEXT_AND_NUMERIC_MONTH_PATTERN;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.TWO_DIGIT_MONTH_FORMAT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.YES;
import static uk.gov.hmcts.reform.bulkscan.helper.BulkScanTransformHelper.transformScanDocuments;

import com.microsoft.applicationinsights.core.dependencies.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationRequest;
import uk.gov.hmcts.reform.bulkscan.utils.DateUtil;

@SuppressWarnings({"PMD", "unchecked"})
@Component
public class BulkScanFL401ConditionalTransformerService {

    public void transform(
            Map<String, String> inputFieldsMap,
            Map<String, Object> populatedMap,
            BulkScanTransformationRequest bulkScanTransformationRequest) {

        LinkedTreeMap relationshipToRespondentTableMap =
                (LinkedTreeMap) populatedMap.get("relationshipToRespondentTable");

        final String relationshipDateComplexStartDate =
                (String) relationshipToRespondentTableMap.get("relationshipDateComplexStartDate");

        if (null != relationshipDateComplexStartDate
                && !relationshipDateComplexStartDate.isEmpty()) {
            relationshipToRespondentTableMap.put(
                    "relationshipDateComplexStartDate",
                    DateUtil.transformDate(
                            relationshipDateComplexStartDate,
                            TEXT_AND_NUMERIC_MONTH_PATTERN,
                            TWO_DIGIT_MONTH_FORMAT));
        }
        final String relationshipDateComplexEndDate =
                (String) relationshipToRespondentTableMap.get("relationshipDateComplexEndDate");

        if (null != relationshipDateComplexEndDate && !relationshipDateComplexEndDate.isEmpty()) {
            relationshipToRespondentTableMap.put(
                    "relationshipDateComplexEndDate",
                    DateUtil.transformDate(
                            relationshipDateComplexEndDate,
                            TEXT_AND_NUMERIC_MONTH_PATTERN,
                            TWO_DIGIT_MONTH_FORMAT));
        }
        final String applicantRelationshipDate =
                (String) relationshipToRespondentTableMap.get("applicantRelationshipDate");

        if (null != applicantRelationshipDate && !applicantRelationshipDate.isEmpty()) {
            relationshipToRespondentTableMap.put(
                    "applicantRelationshipDate",
                    DateUtil.transformDate(
                            applicantRelationshipDate,
                            TEXT_AND_NUMERIC_MONTH_PATTERN,
                            TWO_DIGIT_MONTH_FORMAT));
        }

        final String applicantRelationship =
                (String) relationshipToRespondentTableMap.get("applicantRelationship");

        if (null != applicantRelationship && !applicantRelationship.isEmpty()) {
            relationshipToRespondentTableMap.put(
                    "applicantRelationship",
                    getApplicantRelationships(
                            inputFieldsMap, APPLICANT_RESPONDENT_RELATIONSHIP_FIELDS));
        }

        final String applicantRelationshipOptions =
                (String) relationshipToRespondentTableMap.get("applicantRelationshipOptions");

        if (null != applicantRelationshipOptions
                && !applicantRelationshipOptions.isEmpty()
                && null != inputFieldsMap.get(APPLICANT_RESPONDENT_RELATIONSHIP_NONE_ABOVE)
                && inputFieldsMap
                        .get(APPLICANT_RESPONDENT_RELATIONSHIP_NONE_ABOVE)
                        .equalsIgnoreCase(YES)) {
            relationshipToRespondentTableMap.put(
                    "applicantRelationshipOptions",
                    getApplicantRelationships(
                            inputFieldsMap, APPLICANT_RESPONDENT_RELATIONSHIP_OPTIONS_FIELDS));
        } else {
            relationshipToRespondentTableMap.put("applicantRelationshipOptions", null);
        }

        populatedMap.put(SCAN_DOCUMENTS, transformScanDocuments(bulkScanTransformationRequest));
    }

    private String getApplicantRelationships(
            Map<String, String> inputFieldsMap, String relationshipField) {
        TreeMap<String, String> orderedRelationshipFieldMap = new TreeMap<>();

        if (null == inputFieldsMap || inputFieldsMap.isEmpty()) {
            return null;
        }

        for (Map.Entry<String, String> relationship : inputFieldsMap.entrySet()) {
            if (relationship.getKey().matches(relationshipField)) {
                orderedRelationshipFieldMap.put(relationship.getKey(), relationship.getValue());
            }
        }

        if (!orderedRelationshipFieldMap.isEmpty()) {
            Map<String, String> relationshipFieldMap = buildRelationshipDetailMap();

            for (Map.Entry<String, String> relationShipField :
                    orderedRelationshipFieldMap.entrySet()) {
                if (null != relationShipField.getValue()
                        && relationShipField.getValue().equalsIgnoreCase(YES)) {
                    return getRelationshipDetail(relationShipField.getKey(), relationshipFieldMap);
                }
            }
        }

        return null;
    }

    private String getRelationshipDetail(
            String relationshipField, Map<String, String> relationshipDetailMap) {
        for (Map.Entry<String, String> relationship : relationshipDetailMap.entrySet()) {
            if (relationship.getKey().equalsIgnoreCase(relationshipField)) {
                return relationship.getValue();
            }
        }
        return null;
    }

    private TreeMap<String, String> buildRelationshipDetailMap() {
        TreeMap<String, String> relationshipFieldMap = new TreeMap<>();

        relationshipFieldMap.put(
                "applicantRespondent_Relationship_01", "Married or in a civil partnership");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_02",
                "Formerly married or in a civil partnership");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_03", "Engaged or proposed civil partnership");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_04",
                "Formerly engaged or proposed civil partnership");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_05", "Live together as a couple");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_06", "Formerly lived together as a couple");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_07",
                "Boyfriend, girlfriend or partner who does not live with me");
        relationshipFieldMap.put(
                "applicantRespondent_Relationship_08", "Married or in a civil partnership");
        relationshipFieldMap.put("applicantRespondent_Relationship_09", "None of the above");
        relationshipFieldMap.put("applicantRespondent_Relationship_10", "Father");
        relationshipFieldMap.put("applicantRespondent_Relationship_11", "Mother");
        relationshipFieldMap.put("applicantRespondent_Relationship_12", "Son");
        relationshipFieldMap.put("applicantRespondent_Relationship_13", "Daughter");
        relationshipFieldMap.put("applicantRespondent_Relationship_14", "Brother");
        relationshipFieldMap.put("applicantRespondent_Relationship_15", "Sister");
        relationshipFieldMap.put("applicantRespondent_Relationship_16", "Grandfather");
        relationshipFieldMap.put("applicantRespondent_Relationship_17", "Grandmother");
        relationshipFieldMap.put("applicantRespondent_Relationship_18", "Uncle");
        relationshipFieldMap.put("applicantRespondent_Relationship_19", "Aunt");
        relationshipFieldMap.put("applicantRespondent_Relationship_20", "Nephew");
        relationshipFieldMap.put("applicantRespondent_Relationship_21", "Niece");
        relationshipFieldMap.put("applicantRespondent_Relationship_22", "Cousin");
        relationshipFieldMap.put("applicantRespondent_Relationship_23", "Other - please specify");

        return relationshipFieldMap;
    }
}
