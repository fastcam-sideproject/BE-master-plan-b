package com.example.masterplanbbe.common.log;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        String requestId = ((HttpServletRequest)servletRequest).getHeader("X-RequestID");
        MDC.put("request_id", requestId != null ? requestId : UUID.randomUUID().toString().replaceAll("-", ""));
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.clear();
    }
}
