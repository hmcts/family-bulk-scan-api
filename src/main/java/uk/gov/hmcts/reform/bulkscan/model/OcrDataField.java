package uk.gov.hmcts.reform.bulkscan.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class OcrDataField {
    public String name;
    public String value;
}
