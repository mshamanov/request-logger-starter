package com.mash.requestloggerstarter.logger;

import com.mash.requestloggerstarter.filter.LoggingRequestFilter;
import org.springframework.util.StopWatch;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Interface to log requests and responses from inside {@link LoggingRequestFilter}.
 *
 * @author Mikhail Shamanov
 */
@FunctionalInterface
public interface LoggingRequestHandler {
    /**
     * Logs request and response as well as any other additional information.
     *
     * @param request   cached request
     * @param response  cached response
     * @param stopWatch timing data between request and response
     */
    void log(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, StopWatch stopWatch);
}
