package com.mash.requestloggerstarter.logger;

import com.mash.requestloggerstarter.filter.LoggingRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract log handler to log requests and responses from inside {@link LoggingRequestFilter}.
 * It may be extended to be able to use its methods of gathering information from requests and responses.
 *
 * @author Mikhail Shamanov
 */
public abstract class AbstractLoggingRequestHandler implements LoggingRequestHandler {
    /* REQUEST MAPPING PARAMETERS */
    public static final String METHOD = "Method";
    public static final String HEADERS = "Headers";
    public static final String PARAMETERS = "Parameters";
    public static final String REQUEST_BODY = "Request body";

    /* RESPONSE MAPPING PARAMETERS */
    public static final String STATUS = "Status";
    public static final String RESPONSE_BODY = "Response body";

    /**
     * Gets the information from request and puts it all into a {@link Map}.
     *
     * @param request incoming request
     * @return information on request as a {@link Map}
     */
    protected Map<String, String> getRequestInfo(ContentCachingRequestWrapper request) {
        Map<String, String> map = new LinkedHashMap<>();

        String reqTitle = request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol();
        String headers = this.getHeaders(
                Collections.list(request.getHeaderNames()), h -> Collections.list(request.getHeaders(h)));
        String queryParams = this.getParameters(request.getQueryString());
        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        map.put(AbstractLoggingRequestHandler.METHOD, reqTitle);
        map.put("Url", request.getRequestURL().toString());

        if (!headers.isBlank()) {
            map.put(AbstractLoggingRequestHandler.HEADERS, headers);
        }

        if (!queryParams.isBlank()) {
            map.put(AbstractLoggingRequestHandler.PARAMETERS, queryParams);
        }

        if (!body.isBlank()) {
            map.put(AbstractLoggingRequestHandler.REQUEST_BODY, body);
        }

        return map;
    }

    /**
     * Gets the information from response and puts it all into a {@link Map}.
     *
     * @param response outgoing response
     * @return information on response as a {@link Map}
     */
    protected Map<String, String> getResponseInfo(ContentCachingResponseWrapper response) {
        Map<String, String> map = new LinkedHashMap<>();

        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        String statusInfo = httpStatus.value() + " " + httpStatus.getReasonPhrase();
        String headers = this.getHeaders(response.getHeaderNames(), response::getHeaders);
        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        map.put(AbstractLoggingRequestHandler.STATUS, statusInfo);

        if (!headers.isBlank()) {
            map.put(AbstractLoggingRequestHandler.HEADERS, headers);
        }

        if (!body.isBlank()) {
            map.put(AbstractLoggingRequestHandler.RESPONSE_BODY, body);
        }

        return map;
    }

    /**
     * Concatenates headers as a single {@link String} separated by system-dependent line separator.
     *
     * @param headerNames            collection of header names
     * @param toHeaderValuesFunction function to get a collection of header values knowing its header name
     * @return headers in a single {@link String} separated by system-dependent line separator
     */
    protected String getHeaders(Collection<String> headerNames,
                                Function<? super String, Iterable<? extends CharSequence>> toHeaderValuesFunction) {
        return headerNames.stream()
                .map(h -> h + ": " + String.join(", ", toHeaderValuesFunction.apply(h)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Concatenates query parameters as a single {@link String} separated by system-dependent line separator.
     *
     * @param queryString query as {@link String}
     * @return query parameters in a single {@link String} separated by system-dependent line separator
     */
    protected String getParameters(String queryString) {
        UriComponents build = UriComponentsBuilder.fromUriString("").query(queryString).build();
        MultiValueMap<String, String> queryParams = build.getQueryParams();

        return queryParams.keySet()
                .stream()
                .map(key -> key + " = " + String.join(", ", queryParams.get(key)))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
