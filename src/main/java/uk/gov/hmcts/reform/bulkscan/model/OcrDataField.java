package uk.gov.hmcts.reform.bulkscan.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OcrDataField {
    public String name;
    public String value;
}
