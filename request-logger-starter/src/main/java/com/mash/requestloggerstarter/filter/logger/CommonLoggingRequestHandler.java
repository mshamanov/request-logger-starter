package com.mash.requestloggerstarter.filter.logger;

import com.mash.requestloggerstarter.filter.LoggingRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Default implementation of log handler to log requests and responses from inside {@link LoggingRequestFilter}.
 * It pretties some of the data collected from requests and responses as well as adds the information on
 * how much total time in milliseconds the request had to take to be fully processed.
 *
 * @author Mikhail Shamanov
 */
public class CommonLoggingRequestHandler extends AbstractLoggingRequestHandler {
    private final Logger log = LoggerFactory.getLogger(CommonLoggingRequestHandler.class);

    @Override
    public void log(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, StopWatch stopWatch) {
        Map<String, String> reqMap = this.getRequestInfo(request);
        Map<String, String> resMap = this.getResponseInfo(response);

        resMap.put("Total time", stopWatch.getTotalTimeMillis() + " ms");

        this.log.info("{} {}", "%n%s%n".formatted(this.mapToStr(reqMap)), "%n%s".formatted(this.mapToStr(resMap)));
    }

    private String mapToStr(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(e -> {
                    String key = e.getKey();
                    String value = e.getValue();

                    if (key.equals(CommonLoggingRequestHandler.HEADERS) ||
                        key.equals(CommonLoggingRequestHandler.PARAMETERS)) {
                        value = System.lineSeparator() + Pattern.compile(System.lineSeparator())
                                .splitAsStream(value)
                                .map(line -> "\t└─ " + line)
                                .collect(Collectors.joining(System.lineSeparator()));
                    }

                    return key + ": " + value;
                })
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
