package util;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestSplitUrlTest {
    private static final HttpRequestSplitUrl httpRequestSplitUrl = new HttpRequestSplitUrl();

    @Test
    void splitUrlTest_good() {
        assertThat("/index.html").isEqualTo(httpRequestSplitUrl.extractUrl("GET /index.html"));
    }

    @Test
    void splitUrlTest_bad() {
        assertThat("/index.html").isNotEqualTo(httpRequestSplitUrl.extractUrl("POST /index.html"));
    }
}