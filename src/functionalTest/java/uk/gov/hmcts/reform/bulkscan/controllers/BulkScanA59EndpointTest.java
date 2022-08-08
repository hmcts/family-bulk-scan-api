package uk.gov.hmcts.reform.bulkscan.controllers;

import static uk.gov.hmcts.reform.bulkscan.util.TestResourceUtil.readFileFrom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;

public class BulkScanA59EndpointTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String AUTH_HEADER = "serviceauthorization";
    private static final String A59_VALIDATION_INPUT_PATH =
            "classpath:requests/bulk-scan-a59-validation-input.json";
    private static final String A59_VALIDATION_OUTPUT_PATH =
            "classpath:responses/bulk-scan-a59-validation-output.json";
    private static final String A59_VALIDATION_ERROR_INPUT_PATH =
            "classpath:requests/bulk-scan-a59-error-validation-input.json";
    private static final String A59_VALIDATION_ERROR_OUTPUT_PATH =
            "classpath:responses/bulk-scan-a59-validation-error-output.json";
    private static final String A59_TRANSFORM_INPUT_PATH =
            "classpath:requests/bulk-scan-a59-transform-input.json";
    private static final String A59_TRANSFORM_OUTPUT_PATH =
            "classpath:responses/bulk-scan-a59-transform-output.json";

    private final String targetInstance =
            StringUtils.defaultIfBlank(System.getenv("TEST_URL"), "http://localhost:8090");

    private final RequestSpecification request =
            RestAssured.given().relaxedHTTPSValidation().baseUri(targetInstance);

    @Before
    public void setUp() {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldValidate59BulkScanRequest() throws Exception {
        String bulkScanValidationRequest = readFileFrom(A59_VALIDATION_INPUT_PATH);

        String bulkScanValidationResponse = readFileFrom(A59_VALIDATION_OUTPUT_PATH);

        Response response =
                request.header(AUTH_HEADER, AUTH_HEADER)
                        .body(bulkScanValidationRequest)
                        .when()
                        .contentType("application/json")
                        .post("forms/A59/validate-ocr");

        response.then().assertThat().statusCode(HttpStatus.OK.value());

        JSONAssert.assertEquals(bulkScanValidationResponse, response.getBody().asString(), true);
    }

    @Test
    public void validationErrorForA59BulkScanRequest() throws Exception {
        String bulkScanValidationRequest = readFileFrom(A59_VALIDATION_ERROR_INPUT_PATH);

        String bulkScanValidationResponse = readFileFrom(A59_VALIDATION_ERROR_OUTPUT_PATH);

        Response response =
                request.header(AUTH_HEADER, AUTH_HEADER)
                        .body(bulkScanValidationRequest)
                        .when()
                        .contentType("application/json")
                        .post("forms/A59/validate-ocr");

        response.then().assertThat().statusCode(HttpStatus.OK.value());

        JSONAssert.assertEquals(bulkScanValidationResponse, response.getBody().asString(), true);
    }

    @Test
    public void shouldTransformA59BulkScanRequest() throws Exception {
        String bulkScanTransformRequest = readFileFrom(A59_TRANSFORM_INPUT_PATH);

        String bulkScanTransformResponse = readFileFrom(A59_TRANSFORM_OUTPUT_PATH);

        Response response =
                request.header(AUTH_HEADER, AUTH_HEADER)
                        .body(bulkScanTransformRequest)
                        .when()
                        .contentType("application/json")
                        .post("/transform-exception-record");

        response.then().assertThat().statusCode(HttpStatus.OK.value());

        JSONAssert.assertEquals(bulkScanTransformResponse, response.getBody().asString(), true);
    }
}
