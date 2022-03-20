package com.jpan.retail.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpRequestUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestUtil.class);

    private final String REQUEST_BODY = "requestBody";
    private final String EMPTY = "";


    /**
     * This method is used to extract HttpServletRequest object, we can wapper the postBody into the controller,
     * the HttpServletRequest contains common authentication parameters, so put it here for future.
     */
    public Map<String, String[]> extractBody(final HttpServletRequest request) {
        final Map<String, String[]> map = new HashMap<>();
        final String content = readContentAsString(request);
        if (StringUtils.isNotBlank(content)) {
            map.put(REQUEST_BODY, new String[]{content});
        }
        return map;
    }

    private String readContentAsString(final HttpServletRequest request) {
        String content = EMPTY;
        try (final BufferedReader reader = request.getReader()) {
            if (reader != null) {
                content = IOUtils.toString(reader);
            }
        } catch (IOException e) {
            LOGGER.error("Reading body from request failed", e);
        }
        return content;
    }
}
