package com.mash.requestloggerstarter.filter;

import com.mash.requestloggerstarter.logger.LoggingRequestHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * Filter to log incoming requests and responses to these requests.
 * The actual logging logic must be provided by implementing the {@link LoggingRequestHandler} interface.
 *
 * @author Mikhail Shamanov
 */
public class LoggingRequestFilter extends OncePerRequestFilter {

    private final LoggingRequestHandler loggingRequestHandler;

    @Autowired
    public LoggingRequestFilter(LoggingRequestHandler loggingRequestHandler) {
        this.loggingRequestHandler = loggingRequestHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ContentCachingRequestWrapper req = this.wrapRequest(request);
        ContentCachingResponseWrapper resp = this.wrapResponse(response);

        try {
            filterChain.doFilter(req, resp);
        } finally {
            stopWatch.stop();
            this.loggingRequestHandler.log(req, resp, stopWatch);
            resp.copyBodyToResponse();
        }
    }

    /**
     * Wraps the request as {@link ContentCachingRequestWrapper} to be able to cache the content read from the
     * input stream and reader.
     *
     * @param request incoming request
     * @return request wrapped as {@link ContentCachingRequestWrapper}
     */
    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    /**
     * Wraps the response as {@link ContentCachingResponseWrapper} to be able to cache the content written to the
     * output stream and writer.
     *
     * @param response outgoing response
     * @return response wrapped as {@link ContentCachingResponseWrapper}
     */
    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}