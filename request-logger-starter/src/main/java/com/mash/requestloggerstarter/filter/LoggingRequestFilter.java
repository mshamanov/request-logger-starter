package com.mash.requestloggerstarter.filter;

import com.mash.requestloggerstarter.filter.logger.LoggingRequestHandler;
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

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(req, resp);

        stopWatch.stop();

        this.loggingRequestHandler.log(req, resp, stopWatch);
        resp.copyBodyToResponse();
    }
}