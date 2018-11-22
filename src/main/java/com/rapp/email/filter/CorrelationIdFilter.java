package com.rapp.email.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.rapp.email.constant.ApplicationConstants;

@Component
public class CorrelationIdFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // Get Correlation Id from request headers.
        String correlationId = ((HttpServletRequest) request).getHeader(ApplicationConstants.HEADER_CORRELATION_ID);
        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();

            logger.info("X-Correlation-ID not specified, generating one: " + correlationId);
        }

        MDC.put(ApplicationConstants.HEADER_CORRELATION_ID, correlationId);
        filterChain.doFilter(request, response);
        MDC.remove(ApplicationConstants.HEADER_CORRELATION_ID);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

}

