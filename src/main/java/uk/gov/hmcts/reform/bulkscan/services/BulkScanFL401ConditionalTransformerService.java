package uk.gov.hmcts.reform.bulkscan.services;

import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.NAME_OF_COURT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.NO;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.VALUE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanConstants.YES;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ADDRESS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ADDRESS_LINE1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ADDRESS_LINE2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_CHILD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_CHILD_RELATIONSHIP;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_CONTACT_CONFIDENTIALITY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_FAMILY_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_RESPONDENT_OTHER_RELATIONSHIP_FIELD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_RESPONDENT_SHARE_PARENTAL;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLICANT_SOLICITOR_NAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.APPLYING_FOR_NON_MOLES_STATION_ORDER;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ATTEND_HEARING_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.BAIL_CONDITION_END_DATE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CASE_NUMBER;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CCD_APPLICANT_RELATIONSHIOP_DATE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CCD_RELATIONSHIP_DATE_COMPLEX_END_DATE_FIELD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CCD_RELATIONSHIP_DATE_COMPLEX_START_DATE_FIELD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CCD_RELATIONSHIP_TO_RESPONDENT_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILDREN;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_AGE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_FULL_NAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_LIVE_ADDRESS_ROW_1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_LIVE_ADDRESS_ROW_2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_LIVE_ADDRESS_ROW_3;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.CHILD_LIVE_ADDRESS_ROW_4;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.COMMA;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.COUNTY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.DOB;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.DOES_APPLICANT_HAVE_CHILDREN;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.EMAIL;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.EMPTY_SPACE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FAM_CHILD_DETAILS_ROW_1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FAM_CHILD_DETAILS_ROW_2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FAM_CHILD_DETAILS_ROW_3;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FAM_CHILD_DETAILS_ROW_4;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FL401_APPLICANT_RELATIONSHIP;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FL401_APPLICANT_RELATIONSHIP_OPTIONS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FL401_APPLICANT_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FL401_OTHER_PROCEEDINGS_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FL401_SOLICITOR_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.FULL_NAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.IS_ADDRESS_CONFIDENTIAL;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.IS_EMAIL_ADDRESS_CONFIDENTIAL;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.IS_PHONE_NUMBER_CONFIDENTIAL;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.NEED_FOR_PARENTAL_RESPONSIBILITY;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ONGOING_COURT_PROCEEDING_ROW1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ONGOING_COURT_PROCEEDING_ROW2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.ONGOING_COURT_PROCEEDING_ROW3;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.OTHERS_STOP_RESPONDENT_BEHAVIOUR_OPTIONS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.OTHER_CHILD_LIVE_ADDRESS_ROW_1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.OTHER_CHILD_LIVE_ADDRESS_ROW_2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.OTHER_CHILD_LIVE_ADDRESS_ROW_3;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.OTHER_CHILD_LIVE_ADDRESS_ROW_4;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.PHONE_NUMBER;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.POSTCODE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.POSTTOWN;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.REASON_FOR_ORDER_WITHOUT_GIVING_NOTICE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.REPRESENTATIVE_FIRST_NAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.REPRESENTATIVE_LAST_NAME;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.RESPONDENT_CHILD_RELATIONSHIP;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.RESPONDENT_RESPONSIBLE_FOR_CHILD;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.SPACE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.SPECIAL_MEASURE_AT_COURT_ROW_1;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.SPECIAL_MEASURE_AT_COURT_ROW_2;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.SPECIAL_MEASURE_AT_COURT_ROW_3;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.SPECIAL_MEASURE_AT_COURT_ROW_4;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.STOP_RESPONDENT_BEHAVIOUR_CHILD_OPTIONS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.STOP_RESPONDENT_BEHAVIOUR_OPTIONS;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.STOP_RESPONDENT_BEHAVIOUR_OPTIONS_6;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.STOP_RESPONDENT_BEHAVIOUR_TABLE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.TEXT_AND_NUMERIC_MONTH_PATTERN;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.TWO_DIGIT_MONTH_FORMAT;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.TYPE_OF_CASE;
import static uk.gov.hmcts.reform.bulkscan.constants.BulkScanFl401Constants.WITHOUT_NOTICE_ORDER_TABLE;

import com.microsoft.applicationinsights.core.dependencies.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uk.gov.hmcts.reform.bulkscan.enums.ApplicantRelationshipEnum;
import uk.gov.hmcts.reform.bulkscan.enums.ApplicantRespondentRelationshipEnum;
import uk.gov.hmcts.reform.bulkscan.enums.FL401StopRespondentBehaviourChildEnum;
import uk.gov.hmcts.reform.bulkscan.enums.FL401StopRespondentEnum;
import uk.gov.hmcts.reform.bulkscan.enums.ReasonForOrderWithoutGivingNoticeEnum;
import uk.gov.hmcts.reform.bulkscan.utils.DateUtil;

@SuppressWarnings({"PMD", "unchecked"})
@Component
@Slf4j
public class BulkScanFL401ConditionalTransformerService {

    public static final String INFORMATION_TO_BE_KEPT_CONFIDENTIAL = "Information to be kept confidential";

    public void transform(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {

        // transforming Orders Applying for
        transFormOrdersApplyingFor(populatedMap, inputFieldsMap);

        // transforming without notice order
        tranformReasonForWithoutNotice(populatedMap, inputFieldsMap);

        //transform applicant details
        transformApplicantDetails(populatedMap, inputFieldsMap);

        //transform relationship to respondent
        transformRelationshipToRespondent(populatedMap, inputFieldsMap);

        //transform applicant family
        transformApplicantFamily(populatedMap, inputFieldsMap);

        //transform other proceedings
        transformOtherProceedingsToCourt(populatedMap, inputFieldsMap);

        //transform respondent behaviour
        transformRespondentBehaviourDetails(populatedMap, inputFieldsMap);

        //transform Home
        transformHome(populatedMap, inputFieldsMap);

        //transform attending the hearing
        LinkedTreeMap attendHearingTableMap = (LinkedTreeMap) populatedMap.get(ATTEND_HEARING_TABLE);
        //attendHearingTableMap.put(
        //        SPECIAL_MEASURE_AT_COURT, getFormattedSpecialMeasureAtCourt(inputFieldsMap));
        getInterpreterNeeds(inputFieldsMap, attendHearingTableMap, populatedMap);
    }

    private void transformOtherProceedingsToCourt(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        Map<String, Object> fl401OtherProceedingDetails = new HashMap<>();
        if (YES.equalsIgnoreCase(inputFieldsMap.get("OngoingFamilyCourtProceeding"))) {
            fl401OtherProceedingDetails.put("hasPrevOrOngoingOtherProceeding", "yes");
            populatedMap.put(
                FL401_OTHER_PROCEEDINGS_TABLE,
                transformOngoingFamilyCourtProceedings(inputFieldsMap, fl401OtherProceedingDetails));
            populatedMap.put("fl401OtherProceedingDetails", fl401OtherProceedingDetails);
        } else {
            fl401OtherProceedingDetails.put("hasPrevOrOngoingOtherProceeding", "no");
        }
    }

    private void transformApplicantFamily(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        Map<String, Object> applicantFamily = new HashMap<>();
        if (YES.equalsIgnoreCase(inputFieldsMap.get(NEED_FOR_PARENTAL_RESPONSIBILITY))) {
            applicantFamily.put("applicantFamilyDetails", Map.of("doesApplicantHaveChildren", YES));
            populatedMap.put(APPLICANT_FAMILY_TABLE, transformApplicantChildObjects(inputFieldsMap, applicantFamily));
        } else {
            applicantFamily.put("applicantFamilyDetails", Map.of("doesApplicantHaveChildren", NO));
        }
        populatedMap.putAll(applicantFamily);
    }

    private void transformHome(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        LinkedTreeMap home = (LinkedTreeMap) populatedMap.get("home");
        if (YES.equalsIgnoreCase(String.valueOf(home.get("isThereMortgageOnProperty")))
            && StringUtils.hasText(inputFieldsMap.get("MortgageOnProperty_Person"))) {
            LinkedTreeMap mortgage = (LinkedTreeMap) home.get("mortgages");
            mortgage.put("mortgageNamedAfter", List.of(inputFieldsMap.get("MortgageOnProperty_Person")));
        }
        if (YES.equalsIgnoreCase(String.valueOf(home.get(inputFieldsMap.get("isPropertyRented"))))
            && StringUtils.hasText(inputFieldsMap.get("RentedProperty_Person"))) {
            LinkedTreeMap rented = (LinkedTreeMap) home.get("landlords");
            rented.put("mortgageNamedAfterList", List.of(inputFieldsMap.get("RentedProperty_Person")));
        }
        home.put(CHILDREN, buildTransformChild(populatedMap, inputFieldsMap));
        home.put("livingSituation", buildLivingSituation(inputFieldsMap));
        home.put("familyHome", buildFamilyHome(inputFieldsMap));
        if (StringUtils.hasText(inputFieldsMap.get("occupationOrderAddress_CurrentOccupant"))) {
            home.put("peopleLivingAtThisAddress", List.of(inputFieldsMap.get("occupationOrderAddress_CurrentOccupant")));
        }
        if (StringUtils.hasText(inputFieldsMap.get(CHILD_LIVE_ADDRESS_ROW_1))) {
            home.put("doAnyChildrenLiveAtAddress", YES);
        }
    }

    private void transformRespondentBehaviourDetails(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        if (YES.equalsIgnoreCase(inputFieldsMap.get(APPLYING_FOR_NON_MOLES_STATION_ORDER))) {
            LinkedTreeMap respondentBehaviourTable =
                (LinkedTreeMap) populatedMap.get(STOP_RESPONDENT_BEHAVIOUR_TABLE);
            if (StringUtils.hasText(inputFieldsMap.get(APPLYING_FOR_NON_MOLES_STATION_ORDER))
                && inputFieldsMap.get(APPLYING_FOR_NON_MOLES_STATION_ORDER).equalsIgnoreCase(YES)) {
                Map<String, Object> respondentBehaviourData = new HashMap<>();
                respondentBehaviourData.put(
                    STOP_RESPONDENT_BEHAVIOUR_OPTIONS,
                    getRespondentBehaviourOptions(inputFieldsMap));
                respondentBehaviourData.put(
                    STOP_RESPONDENT_BEHAVIOUR_CHILD_OPTIONS,
                    getRespondentBehaviourChildOptions(inputFieldsMap));
                respondentBehaviourData.put(
                    OTHERS_STOP_RESPONDENT_BEHAVIOUR_OPTIONS,
                    inputFieldsMap.get(STOP_RESPONDENT_BEHAVIOUR_OPTIONS_6));

                respondentBehaviourTable.put(
                    STOP_RESPONDENT_BEHAVIOUR_OPTIONS,
                    String.join(",", getRespondentBehaviourOptions(inputFieldsMap)));
                respondentBehaviourTable.put(
                    STOP_RESPONDENT_BEHAVIOUR_CHILD_OPTIONS,
                    String.join(",", getRespondentBehaviourChildOptions(inputFieldsMap)));
                respondentBehaviourTable.put(
                    OTHERS_STOP_RESPONDENT_BEHAVIOUR_OPTIONS,
                    inputFieldsMap.get(STOP_RESPONDENT_BEHAVIOUR_OPTIONS_6));
                populatedMap.put("respondentBehaviourData", respondentBehaviourData);
            }
        }

    }

    private void transformApplicantDetails(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        LinkedTreeMap fl401ApplicantTable = (LinkedTreeMap) populatedMap.get(FL401_APPLICANT_TABLE);
        LinkedTreeMap fl401Applicant = (LinkedTreeMap) populatedMap.get("applicantsFL401");
        //transform confidentiality for applicant details
        String applicantContactConfidentiality =
            inputFieldsMap.get(APPLICANT_CONTACT_CONFIDENTIALITY);
        transformApplicantConfidentiality(fl401ApplicantTable, applicantContactConfidentiality);
        transformApplicantConfidentiality(fl401Applicant, applicantContactConfidentiality);
        //transform address for applicant details
        if (YES.equalsIgnoreCase(applicantContactConfidentiality)) {
            LinkedTreeMap applicantAddress = transformApplicantAddress();
            fl401ApplicantTable.put(ADDRESS, applicantAddress);
            transformContactDetails(fl401ApplicantTable);
        }
        List<String> contactPreference = new ArrayList<>();
        if (YES.equalsIgnoreCase(inputFieldsMap.get("PreferToBeContactedPost"))) {
            contactPreference.add(inputFieldsMap.get("PreferToBeContactedPost"));
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("PreferToBeContactedEmail"))) {
            contactPreference.add(inputFieldsMap.get("PreferToBeContactedEmail"));
        }
        fl401Applicant.put("applicantPreferredContact", contactPreference);

        fl401Applicant.put("dateOfBirth", DateUtil.buildDate(
            inputFieldsMap.get("applicantDoB_DD"),
            inputFieldsMap.get("applicantDoB_MM"),
            inputFieldsMap.get("applicantDoB_YYYY")));
        fl401ApplicantTable.put("dateOfBirth", DateUtil.buildDate(
            inputFieldsMap.get("applicantDoB_DD"),
            inputFieldsMap.get("applicantDoB_MM"),
            inputFieldsMap.get("applicantDoB_YYYY")));
        LinkedTreeMap fl401SolicitorDetailsTable =
            (LinkedTreeMap) populatedMap.get(FL401_SOLICITOR_TABLE);
        transformSolicitorName(inputFieldsMap, fl401SolicitorDetailsTable, fl401Applicant);
    }

    private void transformRelationshipToRespondent(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        LinkedTreeMap relationshipToRespondentTableMap =
                (LinkedTreeMap) populatedMap.get(CCD_RELATIONSHIP_TO_RESPONDENT_TABLE);
        Map<String, Object> respondentRelationDateInfoObject = new HashMap<>();
        Map<String, Object> relationStartAndEndComplexType = new HashMap<>();

        final String relationshipDateComplexStartDate = DateUtil
            .buildDate(inputFieldsMap.get("relationship_Start_DD"), inputFieldsMap.get("relationship_Start_MM"),
                       inputFieldsMap.get("relationship_Start_YYYY"));

        if (StringUtils.hasText(relationshipDateComplexStartDate)) {
            String startDate = DateUtil.transformDate(
                relationshipDateComplexStartDate,
                TEXT_AND_NUMERIC_MONTH_PATTERN,
                TWO_DIGIT_MONTH_FORMAT);
            relationshipToRespondentTableMap.put(CCD_RELATIONSHIP_DATE_COMPLEX_START_DATE_FIELD, startDate);
            relationStartAndEndComplexType.put(CCD_RELATIONSHIP_DATE_COMPLEX_START_DATE_FIELD, startDate);
        }
        final String relationshipDateComplexEndDate = DateUtil
            .buildDate(inputFieldsMap.get("relationship_End_DD"), inputFieldsMap.get("relationship_End_MM"),
                       inputFieldsMap.get("relationship_End_YYYY"));

        if (null != relationshipDateComplexEndDate && !relationshipDateComplexEndDate.isEmpty()) {
            String endDate = DateUtil.transformDate(
                relationshipDateComplexEndDate,
                TEXT_AND_NUMERIC_MONTH_PATTERN,
                TWO_DIGIT_MONTH_FORMAT);
            relationshipToRespondentTableMap.put(CCD_RELATIONSHIP_DATE_COMPLEX_END_DATE_FIELD, endDate);
            relationStartAndEndComplexType.put(CCD_RELATIONSHIP_DATE_COMPLEX_END_DATE_FIELD, endDate);
        }
        if (!relationStartAndEndComplexType.isEmpty()) {
            respondentRelationDateInfoObject.put("relationStartAndEndComplexType", relationStartAndEndComplexType);
        }
        final String applicantRelationshipDate = DateUtil
            .buildDate(
                inputFieldsMap.get("relationship_PreviousMarried_DD"),
                inputFieldsMap.get("relationship_PreviousMarried_MM"),
                inputFieldsMap.get("relationship_PreviousMarried_YYYY"));

        if (StringUtils.hasText(applicantRelationshipDate)) {
            String applicantRelationshipFormattedDate = DateUtil.transformDate(applicantRelationshipDate,
                                                                               TEXT_AND_NUMERIC_MONTH_PATTERN,
                                                                  TWO_DIGIT_MONTH_FORMAT);
            relationshipToRespondentTableMap.put(CCD_APPLICANT_RELATIONSHIOP_DATE, applicantRelationshipFormattedDate);
            respondentRelationDateInfoObject.put(CCD_APPLICANT_RELATIONSHIOP_DATE, applicantRelationshipFormattedDate);
        }
        if (!respondentRelationDateInfoObject.isEmpty()) {
            populatedMap.put("respondentRelationDateInfoObject", respondentRelationDateInfoObject);
        }
        String applicantRelationship = getApplicantRelationships(inputFieldsMap);
        relationshipToRespondentTableMap.put(FL401_APPLICANT_RELATIONSHIP, null != applicantRelationship
                                                 ? ApplicantRelationshipEnum.getValue(applicantRelationship)
            : applicantRelationship);
        Map<String, Object> applicantRelationshipMap = new HashMap<>();
        if (null != applicantRelationship) {
            applicantRelationshipMap.put(FL401_APPLICANT_RELATIONSHIP, applicantRelationship);
        }
        populatedMap.put("respondentRelationObject", applicantRelationshipMap);

        if (null != inputFieldsMap.get(APPLICANT_RESPONDENT_OTHER_RELATIONSHIP_FIELD)
                && inputFieldsMap.get(APPLICANT_RESPONDENT_OTHER_RELATIONSHIP_FIELD).equalsIgnoreCase(YES)) {
            ApplicantRespondentRelationshipEnum applicantRelationshipOptions =
                getApplicantRespondentRelationshipOptions(inputFieldsMap);
            relationshipToRespondentTableMap.put(FL401_APPLICANT_RELATIONSHIP_OPTIONS,
                                                 applicantRelationshipOptions.getDisplayedValue());
            populatedMap.put("respondentRelationOptions", Map.of(FL401_APPLICANT_RELATIONSHIP_OPTIONS,
                                                                 applicantRelationship));

        } else {
            relationshipToRespondentTableMap.put(FL401_APPLICANT_RELATIONSHIP_OPTIONS, null);
        }
        populatedMap.put(CCD_RELATIONSHIP_TO_RESPONDENT_TABLE, relationshipToRespondentTableMap);
    }

    private void getInterpreterNeeds(Map<String, String> inputFieldsMap, LinkedTreeMap attendHearingTableMap,
                                     Map<String, Object> populatedMap) {
        if (YES.equalsIgnoreCase(inputFieldsMap.get("InterpreterNeededAtCourt"))) {
            if (StringUtils.hasText(inputFieldsMap.get("interpreterNeededAtCourt_Language"))
                || StringUtils.hasText(inputFieldsMap.get("interpreterNeededAtCourt_Dialect"))) {
                Map<String, Object> values = new HashMap<>();
                List<String> languageAndDialect = new ArrayList<>();
                if (StringUtils.hasText(inputFieldsMap.get("interpreterNeededAtCourt_Language"))) {
                    languageAndDialect.add(inputFieldsMap.get("interpreterNeededAtCourt_Language"));
                }
                if (StringUtils.hasText(inputFieldsMap.get("interpreterNeededAtCourt_Dialect"))) {
                    languageAndDialect.add(inputFieldsMap.get("interpreterNeededAtCourt_Dialect"));
                }
                values.put("party", List.of("applicant"));
                values.put("name", "self");
                values.put("language", String.join(",", languageAndDialect));
                //attendHearingTableMap.put("interpreterNeeds", List.of(Map.ofEntries(
                //                                                          Map.entry("id", UUID.randomUUID()),
                //                                                          Map.entry("value", values)
                //                                                      )));
                log.info("interpreterNeeds: {}", attendHearingTableMap);
                populatedMap.put("interpreterNeeds", List.of(Map.ofEntries(
                    Map.entry("id", UUID.randomUUID()),
                    Map.entry("value", values)
                )));
            }
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("disabilitySupport_NeededAtCourt"))) {
            populatedMap.put("adjustmentsRequired", inputFieldsMap.get("disabilitySupport_details"));
        }
        String specialArrangements = getFormattedSpecialMeasureAtCourt(inputFieldsMap);
        if (null != specialArrangements) {
            populatedMap.put("isSpecialArrangementsRequired", YES);

        }
    }

    private void tranformReasonForWithoutNotice(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        List<String> reasonForOrderWithoutGivingNoticeList = new ArrayList<>();
        if (StringUtils.hasText(inputFieldsMap.get("orderWithoutGivingNoticeReason1"))) {
            reasonForOrderWithoutGivingNoticeList.add(ReasonForOrderWithoutGivingNoticeEnum.harmToApplicantOrChild.toString());
        }
        if (StringUtils.hasText(inputFieldsMap.get("orderWithoutGivingNoticeReason2"))) {
            reasonForOrderWithoutGivingNoticeList.add(ReasonForOrderWithoutGivingNoticeEnum.deferringApplicationIfNotImmediate
                                                          .toString());
        }
        if (StringUtils.hasText(inputFieldsMap.get("orderWithoutGivingNoticeReason3"))) {
            reasonForOrderWithoutGivingNoticeList.add(ReasonForOrderWithoutGivingNoticeEnum.prejudiced.toString());
        }
        LinkedTreeMap reasonForOrderWithoutGivingNotice = (LinkedTreeMap) populatedMap.get("reasonForOrderWithoutGivingNotice");
        reasonForOrderWithoutGivingNotice.put("reasonForOrderWithoutGivingNotice", reasonForOrderWithoutGivingNoticeList);

        //transform Bail conditions
        LinkedTreeMap withoutNoticeOrderTableMap =
            (LinkedTreeMap) populatedMap.get(WITHOUT_NOTICE_ORDER_TABLE);
        if (YES.equalsIgnoreCase(inputFieldsMap.get("respondentBailConditions"))) {
            LinkedTreeMap bailConditions = (LinkedTreeMap) populatedMap.get("bailDetails");
            String bailConditionEndDate = DateUtil
                .transformDate(DateUtil.buildDate(inputFieldsMap.get("respondentBailConditions_EndDate_Day"),
                                                  inputFieldsMap.get("respondentBailConditions_EndDate_Month"),
                                                  inputFieldsMap.get("respondentBailConditions_EndDate_Year")),
                               TEXT_AND_NUMERIC_MONTH_PATTERN,
                               TWO_DIGIT_MONTH_FORMAT);
            bailConditions.put("bailConditionEndDate", bailConditionEndDate);
            withoutNoticeOrderTableMap.put(BAIL_CONDITION_END_DATE, bailConditionEndDate);
        }
        withoutNoticeOrderTableMap.put(REASON_FOR_ORDER_WITHOUT_GIVING_NOTICE,
                                       String.join(",", reasonForOrderWithoutGivingNoticeList));
    }

    private void transFormOrdersApplyingFor(Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        LinkedTreeMap typeOfApplicationOrders =
            (LinkedTreeMap) populatedMap.get("typeOfApplicationOrders");
        List<String> orderApplyingFor = new ArrayList<>();
        if (StringUtils.hasText(inputFieldsMap.get("orderApplyingFor_nonmolestation"))) {
            orderApplyingFor.add("nonMolestationOrder");
        }
        if (StringUtils.hasText(inputFieldsMap.get("orderApplyingFor_occupation"))) {
            orderApplyingFor.add("occupationOrder");
        }
        typeOfApplicationOrders.put("orderType", orderApplyingFor);
    }

    private List<String> buildFamilyHome(Map<String, String> inputFieldsMap) {
        List<String> familyHome = new ArrayList<>();
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToFamilyHome_Row1"))) {
            familyHome.add("payForRepairs");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToFamilyHome_Row2"))) {
            familyHome.add("payOrContributeRent");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToFamilyHome_Row3"))) {
            familyHome.add("useHouseholdContents");
        }
        return familyHome;
    }

    private List<String> buildLivingSituation(Map<String, String> inputFieldsMap) {
        List<String> livingSituation = new ArrayList<>();
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToLivingSituation_Row1"))) {
            livingSituation.add("ableToStayInHome");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToLivingSituation_Row2"))) {
            livingSituation.add("ableToReturnHome");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToLivingSituation_Row3"))) {
            livingSituation.add("restrictFromEnteringHome");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToLivingSituation_Row4"))) {
            livingSituation.add("awayFromHome");
        }
        if (StringUtils.hasText(inputFieldsMap.get("ChangesToLivingSituation_Row5"))) {
            livingSituation.add("limitRespondentInHome");
        }
        return livingSituation;
    }

    private String getFormattedSpecialMeasureAtCourt(Map<String, String> inputFieldsMap) {
        String row1 = inputFieldsMap.get(SPECIAL_MEASURE_AT_COURT_ROW_1);
        String row2 = inputFieldsMap.get(SPECIAL_MEASURE_AT_COURT_ROW_2);
        String row3 = inputFieldsMap.get(SPECIAL_MEASURE_AT_COURT_ROW_3);
        String row4 = inputFieldsMap.get(SPECIAL_MEASURE_AT_COURT_ROW_4);
        String str =
                (row1 != null ? row1 + COMMA : SPACE)
                        + (row2 != null ? row2 + COMMA : SPACE)
                        + (row3 != null ? row3 + COMMA : SPACE)
                        + (row4 != null ? row4 : SPACE);
        return StringUtils.hasText(str) ? str : null;
    }

    private List buildTransformChild(
            Map<String, Object> populatedMap, Map<String, String> inputFieldsMap) {
        ArrayList<LinkedTreeMap> children = new ArrayList<>();

        final String row1 = inputFieldsMap.get(CHILD_LIVE_ADDRESS_ROW_1);
        final String row2 = inputFieldsMap.get(CHILD_LIVE_ADDRESS_ROW_2);
        final String row3 = inputFieldsMap.get(CHILD_LIVE_ADDRESS_ROW_3);
        final String row4 = inputFieldsMap.get(CHILD_LIVE_ADDRESS_ROW_4);
        final String otherChildrenRow1 = inputFieldsMap.get(OTHER_CHILD_LIVE_ADDRESS_ROW_1);
        final String otherChildrenRow2 = inputFieldsMap.get(OTHER_CHILD_LIVE_ADDRESS_ROW_2);
        final String otherChildrenRow3 = inputFieldsMap.get(OTHER_CHILD_LIVE_ADDRESS_ROW_3);
        final String otherChildrenRow4 = inputFieldsMap.get(OTHER_CHILD_LIVE_ADDRESS_ROW_4);
        ArrayList<String> childInput = new ArrayList<>();
        childInput.add(row1);
        childInput.add(row2);
        childInput.add(row3);
        childInput.add(row4);
        childInput.add(otherChildrenRow1);
        childInput.add(otherChildrenRow2);
        childInput.add(otherChildrenRow3);
        childInput.add(otherChildrenRow4);

        for (String input : childInput) {
            LinkedTreeMap<String, String> childDetails = new LinkedTreeMap<>();
            final LinkedTreeMap<String, Object> childrenLinkedTreeMap = new LinkedTreeMap();
            if (null != input) {
                final String[] columnDetails = input.split(COMMA);
                final String childName = columnDetails[0];

                String childAge = null;
                String isRespondentResponsibleForChild = null;

                try {
                    if (columnDetails.length > 1) {
                        childAge = columnDetails[1];
                        isRespondentResponsibleForChild = columnDetails[2];
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                childDetails.put(CHILD_FULL_NAME, childName);
                childDetails.put(CHILD_AGE, childAge);
                childDetails.put(RESPONDENT_RESPONSIBLE_FOR_CHILD, isRespondentResponsibleForChild);
                childDetails.put("keepChildrenInfoConfidential", NO);
                childrenLinkedTreeMap.put("id", UUID.randomUUID().toString());
                childrenLinkedTreeMap.put(VALUE, childDetails);
                children.add(childrenLinkedTreeMap);
            }
        }
        return children;
    }

    private Map<String, Object> transformApplicantChildObjects(Map<String, String> inputFieldsMap,
                                                               Map<String, Object> applicantFamily) {

        Map<String, Object> applicantChildMap = new HashMap<>();
        applicantChildMap.put(DOES_APPLICANT_HAVE_CHILDREN, inputFieldsMap.get(NEED_FOR_PARENTAL_RESPONSIBILITY));
        List<Map<String, Object>> children = transformApplicantChild(inputFieldsMap);
        applicantChildMap.put(APPLICANT_CHILD, children);
        applicantFamily.put("applicantChildDetails", children);
        return applicantChildMap;
    }

    private List<Map<String, Object>> transformApplicantChild(Map<String, String> inputFieldsMap) {
        ArrayList<Map<String, Object>> childrenDetailsLst = new ArrayList<>();

        final String row1 = inputFieldsMap.get(FAM_CHILD_DETAILS_ROW_1);
        final String row2 = inputFieldsMap.get(FAM_CHILD_DETAILS_ROW_2);
        final String row3 = inputFieldsMap.get(FAM_CHILD_DETAILS_ROW_3);
        final String row4 = inputFieldsMap.get(FAM_CHILD_DETAILS_ROW_4);

        ArrayList<String> childInput = new ArrayList<>();
        childInput.add(row1);
        childInput.add(row2);
        childInput.add(row3);
        childInput.add(row4);

        for (String input : childInput) {
            Map<String, String> childDetails = new LinkedTreeMap<>();
            Map<String, Object> childrenLinkedTreeMap = new HashMap<>();

            if (StringUtils.hasText(input)) {
                final String[] columnDetails = input.split(COMMA);

                final String childName = columnDetails[0];

                String childDoB = null;
                String yourRelationshipWithChild = null;
                String doYouAndRespondentHaveParentalResponsibility = null;
                String respondentsRelationshipWithChild = null;

                try {
                    if (columnDetails.length > 1) {
                        childDoB = columnDetails[1];
                        yourRelationshipWithChild = columnDetails[2];
                        doYouAndRespondentHaveParentalResponsibility = columnDetails[3];
                        respondentsRelationshipWithChild = columnDetails[4];
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                childDetails.put(FULL_NAME, childName);
                if (StringUtils.hasText(childDoB)) {
                    childDetails.put(DOB, DateUtil.transformDate(childDoB, TEXT_AND_NUMERIC_MONTH_PATTERN,
                                                                 TWO_DIGIT_MONTH_FORMAT));
                }
                childDetails.put(APPLICANT_CHILD_RELATIONSHIP, yourRelationshipWithChild);
                if (StringUtils.hasText(doYouAndRespondentHaveParentalResponsibility)) {
                    childDetails.put(APPLICANT_RESPONDENT_SHARE_PARENTAL, doYouAndRespondentHaveParentalResponsibility
                        .toLowerCase().contains("yes") ? "Yes" : "No");
                }
                childDetails.put(RESPONDENT_CHILD_RELATIONSHIP, respondentsRelationshipWithChild);
                childrenLinkedTreeMap.put("id", UUID.randomUUID().toString());
                childrenLinkedTreeMap.put(VALUE, childDetails);

                childrenDetailsLst.add(childrenLinkedTreeMap);
            }
        }
        return childrenDetailsLst;
    }

    private List transformOngoingFamilyCourtProceedings(Map<String, String> inputFieldsMap,
                                                        Map<String, Object> fl401OtherProceedingDetails) {
        ArrayList<Map<String, Object>> familyCourtProceedingsDetails = new ArrayList<>();
        final String row1 = inputFieldsMap.get(ONGOING_COURT_PROCEEDING_ROW1);
        final String row2 = inputFieldsMap.get(ONGOING_COURT_PROCEEDING_ROW2);
        final String row3 = inputFieldsMap.get(ONGOING_COURT_PROCEEDING_ROW3);

        ArrayList<String> courtInput = new ArrayList<>();
        courtInput.add(row1);
        courtInput.add(row2);
        courtInput.add(row3);

        for (String input : courtInput) {
            Map<String, String> caseDetails = new HashMap<>();

            final Map<String, Object> childrenLinkedTreeMap = new HashMap<>();

            if (StringUtils.hasText(input)) {
                final String[] columnDetails = input.split(COMMA);

                final String nameOfCourt = columnDetails[0];

                String caseNumber = null;
                String typeOfCase = null;

                try {
                    if (columnDetails.length > 1) {
                        caseNumber = columnDetails[1];
                        typeOfCase = columnDetails[2];
                    }
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }

                caseDetails.put(NAME_OF_COURT, nameOfCourt);
                caseDetails.put(CASE_NUMBER, caseNumber);
                caseDetails.put(TYPE_OF_CASE, typeOfCase);
                childrenLinkedTreeMap.put("id", UUID.randomUUID());
                childrenLinkedTreeMap.put(VALUE, caseDetails);
                familyCourtProceedingsDetails.add(childrenLinkedTreeMap);
            }
        }
        fl401OtherProceedingDetails.put("fl401OtherProceedings", familyCourtProceedingsDetails);
        return familyCourtProceedingsDetails;
    }

    private String getApplicantRelationships(
            Map<String, String> inputFieldsMap) {

        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_1"))) {
            return ApplicantRelationshipEnum.marriedOrCivil.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_2"))) {
            return ApplicantRelationshipEnum.formerlyMarriedOrCivil.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_3"))) {
            return ApplicantRelationshipEnum.engagedOrProposed.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_4"))) {
            return ApplicantRelationshipEnum.formerlyEngagedOrProposed.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_5"))) {
            return ApplicantRelationshipEnum.liveTogether.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_6"))) {
            return ApplicantRelationshipEnum.foremerlyLivedTogether.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_7"))) {
            return ApplicantRelationshipEnum.bfGfOrPartnerNotLivedTogether.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_8"))) {
            return ApplicantRelationshipEnum.formerBfGfOrPartnerNotLivedTogether.getId();
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_9"))) {
            return ApplicantRelationshipEnum.noneOfTheAbove.getId();
        }
        return null;
    }

    private ApplicantRespondentRelationshipEnum getApplicantRespondentRelationshipOptions(Map<String, String> inputFieldsMap) {
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_10"))) {
            return ApplicantRespondentRelationshipEnum.father;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_11"))) {
            return ApplicantRespondentRelationshipEnum.mother;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_12"))) {
            return ApplicantRespondentRelationshipEnum.son;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_13"))) {
            return ApplicantRespondentRelationshipEnum.daughter;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_14"))) {
            return ApplicantRespondentRelationshipEnum.brother;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_15"))) {
            return ApplicantRespondentRelationshipEnum.sister;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_16"))) {
            return ApplicantRespondentRelationshipEnum.grandfather;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_17"))) {
            return ApplicantRespondentRelationshipEnum.grandmother;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_18"))) {
            return ApplicantRespondentRelationshipEnum.uncle;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_19"))) {
            return ApplicantRespondentRelationshipEnum.aunt;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_20"))) {
            return ApplicantRespondentRelationshipEnum.nephew;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_21"))) {
            return ApplicantRespondentRelationshipEnum.niece;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_22"))) {
            return ApplicantRespondentRelationshipEnum.cousin;
        }
        if (YES.equalsIgnoreCase(inputFieldsMap.get("appliantRespondent_Relationship_23"))) {
            return ApplicantRespondentRelationshipEnum.other;
        }
        return null;
    }

    private List<String> getRespondentBehaviourOptions(Map<String, String> inputFieldsMap) {
        List<String> respondentBehaviourOptions = new ArrayList<>();
        for (FL401StopRespondentEnum l : EnumSet.allOf(FL401StopRespondentEnum.class)) {
            String key = l.getKey();
            if (null != inputFieldsMap.get(key) && inputFieldsMap.get(key).equalsIgnoreCase(YES)) {
                respondentBehaviourOptions.add(FL401StopRespondentEnum.getValue(l.name()));
            }
        }
        return respondentBehaviourOptions;
    }

    private List<String> getRespondentBehaviourChildOptions(Map<String, String> inputFieldsMap) {
        List<String> respondentBehaviourChildOptions = new ArrayList<>();
        for (FL401StopRespondentBehaviourChildEnum l :
                EnumSet.allOf(FL401StopRespondentBehaviourChildEnum.class)) {
            String key = l.getKey();
            if (null != inputFieldsMap.get(key) && inputFieldsMap.get(key).equalsIgnoreCase(YES)) {
                respondentBehaviourChildOptions.add(FL401StopRespondentBehaviourChildEnum.getValue(l.name()));
            }
        }
        return respondentBehaviourChildOptions;
    }

    private void transformApplicantConfidentiality(
            LinkedTreeMap applicantDetails, String applicantContactConfidentiality) {

        if (null != applicantContactConfidentiality
                && applicantContactConfidentiality.equalsIgnoreCase(YES)) {

            applicantDetails.put(IS_ADDRESS_CONFIDENTIAL, YES);
            applicantDetails.put(IS_PHONE_NUMBER_CONFIDENTIAL, YES);
            applicantDetails.put(IS_EMAIL_ADDRESS_CONFIDENTIAL, YES);

        } else if (null != applicantContactConfidentiality
                && applicantContactConfidentiality.equalsIgnoreCase(NO)) {
            applicantDetails.put(IS_ADDRESS_CONFIDENTIAL, NO);
            applicantDetails.put(IS_PHONE_NUMBER_CONFIDENTIAL, NO);
            applicantDetails.put(IS_EMAIL_ADDRESS_CONFIDENTIAL, NO);
        }
    }

    private LinkedTreeMap transformApplicantAddress() {
        LinkedTreeMap address = new LinkedTreeMap<>();
        address.put(ADDRESS_LINE1, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
        address.put(ADDRESS_LINE2, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
        address.put(POSTTOWN, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
        address.put(COUNTY, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
        address.put(POSTCODE, null);
        return address;
    }

    private void transformContactDetails(
            LinkedTreeMap applicantDetails) {
        applicantDetails.put(PHONE_NUMBER, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
        applicantDetails.put(EMAIL, INFORMATION_TO_BE_KEPT_CONFIDENTIAL);
    }

    private void transformSolicitorName(
        Map<String, String> inputFieldsMap, LinkedTreeMap fl401SolicitorDetailsTable, LinkedTreeMap fl401Applicant) {

        final String applicantSolicitorName = inputFieldsMap.get(APPLICANT_SOLICITOR_NAME);
        if (applicantSolicitorName != null) {
            final String[] representativeName = applicantSolicitorName.split(EMPTY_SPACE);
            fl401Applicant.put(REPRESENTATIVE_FIRST_NAME, applicantSolicitorName);
            fl401SolicitorDetailsTable.put(REPRESENTATIVE_FIRST_NAME, representativeName[0]);
            if (representativeName.length == 2) {
                fl401SolicitorDetailsTable.put(REPRESENTATIVE_LAST_NAME, representativeName[1]);
                fl401Applicant.put(REPRESENTATIVE_LAST_NAME, representativeName[1]);
            }
        }
    }
}
