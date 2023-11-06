package xyz.crediblepulse.crediblepulsebackend.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.crediblepulse.crediblepulsebackend.config.security.CurrentUserProvider;
import xyz.crediblepulse.crediblepulsebackend.utils.HttpRequestResponseUtil;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class UsernameIPLoggingFilter implements Filter {

    private static final String USER_NAME_KEY = "username";
    private static final String ROLE_NAME_KEY = "role";
    private static final String CLIENT_NAME_KEY = "clientId";
    private static final String USER_IP_KEY = "ip";
    private static final String REQUEST_ID = "requestId";

    private final CurrentUserProvider currentUserProvider;

    private static void cleanMDC() {
        MDC.remove(USER_IP_KEY);
        MDC.remove(USER_NAME_KEY);
        MDC.remove(ROLE_NAME_KEY);
        MDC.remove(CLIENT_NAME_KEY);
        MDC.remove(REQUEST_ID);
    }

    private static void registerRequestID(String requestID) {
        MDC.put(REQUEST_ID, requestID);
    }

    static void registerUsername(String username) {

        if (StringUtils.isNotBlank(username)) {
            MDC.put(USER_NAME_KEY, username);
        } else {
            MDC.put(USER_NAME_KEY, "Anonymous");
        }
    }

    static void registerRole(String role) {

        if (StringUtils.isNotBlank(role)) {
            MDC.put(ROLE_NAME_KEY, role);
        } else {
            MDC.put(ROLE_NAME_KEY, "Incognito");
        }
    }

    static void registerClientId(String clientId) {

        if (StringUtils.isNotBlank(clientId)) {
            MDC.put(CLIENT_NAME_KEY, clientId);
        } else {
            MDC.put(CLIENT_NAME_KEY, "AnonymousC");
        }
    }

    private static void registerIp(String remoteAddress) {
        MDC.put(USER_IP_KEY, remoteAddress);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // Nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        registerUsername(currentUserProvider.getUsername());

        registerClientId(currentUserProvider.getClientId());

        registerIp(HttpRequestResponseUtil.getClientIpAddressIfServletRequestExist());

        registerRequestID(req.getParameter(REQUEST_ID));

        try {
            chain.doFilter(request, response);
        } finally {
            cleanMDC();
        }
    }
}
