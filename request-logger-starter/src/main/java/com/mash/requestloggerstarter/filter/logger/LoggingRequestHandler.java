package com.mash.requestloggerstarter.filter.logger;

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
    void log(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, StopWatch stopWatch);
}
