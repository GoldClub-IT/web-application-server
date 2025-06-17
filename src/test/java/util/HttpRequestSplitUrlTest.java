package util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestSplitUrlTest {
    private static final HttpRequestSplitUrl httpRequestSplitUrl = new HttpRequestSplitUrl();

    @Test
    public void splitUrlTest_good() {
        assertThat("/index.html").isEqualTo(httpRequestSplitUrl.extractUrl("GET /index.html"));
    }

    @Test
    public void splitUrlTest_bad() {
        assertThat("/index.html").isNotEqualTo(httpRequestSplitUrl.extractUrl("POST /index.html"));
    }
}