package com.nemo.mypath.core.filters;

import com.nemo.mypath.core.constants.PathParams;
import com.nemo.mypath.core.security.CurrentUserProvider;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(0)
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    private static final String IGNORED_REQUEST_MAPPING = "/actuator/";

    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public RequestResponseLoggingFilter(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest httpServletRequest,
            @Nonnull HttpServletResponse httpServletResponse,
            @Nonnull FilterChain filterChain)
            throws ServletException, IOException {

        if (isNotIgnored(httpServletRequest)) {
            LOGGER.info(
                    "Request begin user:{} requestId:{} method:{} uri{} queryParams: [ {} ]",
                    currentUserProvider.getUsername(),
                    httpServletRequest.getParameter(PathParams.REQUEST_ID),
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getQueryString());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        if (isNotIgnored(httpServletRequest)) {
            LOGGER.info(
                    "Request end user:{} requestId:{} method:{} uri{} status:{}",
                    currentUserProvider.getUsername(),
                    httpServletRequest.getParameter(PathParams.REQUEST_ID),
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletResponse.getStatus());
        }
    }

    private boolean isNotIgnored(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI() != null
                && !httpServletRequest.getRequestURI().contains(IGNORED_REQUEST_MAPPING);
    }
}
