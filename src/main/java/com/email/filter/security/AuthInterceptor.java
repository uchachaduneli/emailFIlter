package com.email.filter.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        String uri = request.getRequestURI();

        if (userId == null && request.getHeader("X-Requested-With") == null) {
            if (uri.startsWith(request.getContextPath())) {
                uri = uri.replace(request.getContextPath(), "");
            }

            if (uri.length() > 0 && !uri.equals("/")) {
                response.sendRedirect("login");
            } else {
                response.sendRedirect("login");
            }
            return false;
        } else if (userId == null) {
            response.sendError(353, "Your session Expired");
            return false;
        }
        return true;
    }
}
