package com.ugurcansandik.Avansas.config;

import com.ugurcansandik.Avansas.services.JwtService;
import com.ugurcansandik.Avansas.services.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String LOGIN_REDIRECT = "http://localhost:8080/login";
    private static final String AUTH_LOGOUT = "/auth/logout";
    private static final String USER = "user";
    private final JwtService jwtService;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if(request.getRequestURI().equals(AUTH_LOGOUT)){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(USER)){
                    deleteCookie(response,cookie.getName());
                }
            }
            response.sendRedirect(LOGIN_REDIRECT);
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = null;
        if(cookies == null){
            filterChain.doFilter(request,response);
            return;
        }

        for (Cookie cookie : cookies){
            if (cookie.getName().equals(USER)){
                jwt = cookie.getValue();
            }
        }
        if (jwt == null){
            filterChain.doFilter(request,response);
            return;
        }

        String userName = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails userDetails = userDetailService.getUserDetailsService().loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt,userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        response.addHeader("role", userDetailService.findByUsername(userName).get().getRoleEnum().toString());

        filterChain.doFilter(request,response);
    }

    private HttpServletResponse deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return response;
    }
}