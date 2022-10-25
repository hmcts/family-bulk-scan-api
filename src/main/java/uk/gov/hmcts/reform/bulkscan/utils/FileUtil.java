package uk.gov.hmcts.reform.bulkscan.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class FileUtil {
    public static final String TEST_RESOURCE_NOT_FOUND = "Could not find resource in path";

    private FileUtil() {}

    public static String loadJson(final String filePath) throws IOException {
        return new String(loadResource(filePath), Charset.forName("utf-8"));
    }

    public static byte[] loadResource(final String filePath) throws IOException {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(filePath);

            if (url == null) {
                throw new IllegalArgumentException(
                        String.format(TEST_RESOURCE_NOT_FOUND + "%s", filePath));
            }
            return Files.readAllBytes(Paths.get(url.toURI()));
        } catch (IOException | URISyntaxException | IllegalArgumentException ioException) {
            throw new RuntimeException(TEST_RESOURCE_NOT_FOUND + filePath, ioException);
        }
    }

    public static <T> T loadJsonToObject(String filePath, Class<T> type) {
        try {
            return new ObjectMapper().readValue(loadJson(filePath), type);
        } catch (Exception e) {
            throw new RuntimeException(TEST_RESOURCE_NOT_FOUND + filePath, e);
        }
    }

    public static <T> String objectToJson(T object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Could not write object to Json ", e);
        }
    }
}
