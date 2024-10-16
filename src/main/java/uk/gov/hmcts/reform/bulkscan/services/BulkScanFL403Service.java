package uk.gov.hmcts.reform.bulkscan.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.bulkscan.config.BulkScanFormValidationConfigManager;
import uk.gov.hmcts.reform.bulkscan.helper.BulkScanValidationHelper;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationRequest;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationRequestNew;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanTransformationResponse;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanValidationRequest;
import uk.gov.hmcts.reform.bulkscan.model.BulkScanValidationResponse;
import uk.gov.hmcts.reform.bulkscan.model.FormType;
import uk.gov.hmcts.reform.bulkscan.model.OcrDataField;

@NoArgsConstructor
@Service
public class BulkScanFL403Service implements BulkScanService {

    @Autowired BulkScanFormValidationConfigManager configManager;

    @Autowired BulkScanValidationHelper bulkScanValidationHelper;

    @Override
    public BulkScanValidationResponse validate(
            BulkScanValidationRequest bulkScanValidationRequest) {
        // Validating the Fields..
        return bulkScanValidationHelper.validateMandatoryAndOptionalFields(
                bulkScanValidationRequest.getOcrdatafields(),
                configManager.getValidationConfig(FormType.FL403));
    }

    @Override
    public FormType getCaseType() {
        return FormType.FL403;
    }

    @Override
    public BulkScanTransformationResponse transform(
            BulkScanTransformationRequest bulkScanTransformationRequest) {
        // TODO transformation logic
        return null;
    }

    @Override
    public BulkScanTransformationResponse transformNew(BulkScanTransformationRequestNew bulkScanTransformationRequest) {
        return transform(BulkScanTransformationRequest.builder()
                             .ocrdatafields(bulkScanTransformationRequest.getOcrdatafields()
                                                .stream().map(ocr -> OcrDataField.builder().name(ocr.getName())
                                     .value(ocr.getValue()).build()).toList())
                             .formType(bulkScanTransformationRequest.formType)
                             .caseTypeId(bulkScanTransformationRequest.caseTypeId)
                             .deliveryDate(bulkScanTransformationRequest.deliveryDate)
                             .id(bulkScanTransformationRequest.id)
                             .journeyClassification(bulkScanTransformationRequest.journeyClassification)
                             .openingDate(bulkScanTransformationRequest.openingDate)
                             .poBox(bulkScanTransformationRequest.poBox)
                             .poBoxJurisdiction(bulkScanTransformationRequest.poBoxJurisdiction)
                             .scannedDocuments(bulkScanTransformationRequest.scannedDocuments)
                             .build());
    }
}
