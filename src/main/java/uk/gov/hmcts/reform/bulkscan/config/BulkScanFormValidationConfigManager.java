package uk.gov.hmcts.reform.bulkscan.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.bulkscan.model.FormType;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "uk.gov.hmcts.form-validation")
@Configuration
@Setter
@Getter
public class BulkScanFormValidationConfigManager {

    private Map<String, ValidationConfig> caseTypeofApplication;

    @Data
    public static class ValidationConfig {
        List<String> mandatoryFields;
        List<String> optionalFields;
        RegexValidationConfig regexValidationFields;
        MandatoryGroupField mandatoryGroupField;
    }

    @Data
    public static class MandatoryGroupField {
        Group dateFields;
    }

    @Data
    public static class Group {
        List<String> fieldNames;
    }

    @Data
    public static class RegexValidationConfig {
        RegexFieldsConfig dateFields;
        RegexFieldsConfig emailFields;
        RegexFieldsConfig numericFields;
        RegexFieldsConfig postCodeFields;
        RegexFieldsConfig phoneNumberFields;
        RegexFieldsConfig faxNumberFields;
        RegexFieldsConfig xorConditionalFields;
        RegexFieldsConfig alphaNumericFields;
    }

    @Data
    public static class RegexFieldsConfig {
        List<String> fieldNames;
        String regex;
    }

    public ValidationConfig getValidationConfig(FormType formType) {
        return caseTypeofApplication.get(formType.name());
    }
}
