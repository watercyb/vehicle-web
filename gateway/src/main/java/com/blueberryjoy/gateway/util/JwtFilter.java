package com.blueberryjoy.gateway.util;

import com.blueberryjoy.gateway.proxy.UserProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final ObjectFactory<UserContext> userContextObjectFactory;
    private final UserProxy UserProxy;


    private void setContext(String payloadJson) {
        Optional.of(payloadJson)
                .map(Payload::generate)
                .map(Payload::getUserId)
                .map(UserProxy::getUser)
                .ifPresent(userContextObjectFactory.getObject()::setUser);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get the content of simple HTTP request.
        Optional.of(request)
                .map(HttpServletRequest.class::cast)
                .map(req->req.getHeader("Authorization"))
                .map(token->token.split("\\."))
                .filter(chunks->chunks.length>1)
                .map(chunks-> Base64.getUrlDecoder().decode(chunks[1]))
                .map(String::new)
                .ifPresent(this::setContext);
        filterChain.doFilter(request,response);
    }
}
