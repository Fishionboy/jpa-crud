package com.apicrud.jpa.security.filter;

import static com.apicrud.jpa.security.TokenJwtConfig.CONTENT_TYPE;
import static com.apicrud.jpa.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.apicrud.jpa.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.apicrud.jpa.security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.apicrud.jpa.security.SimpledGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

       String header = request.getHeader(HEADER_AUTHORIZATION);

       if(header == null || !header.startsWith(PREFIX_TOKEN)){
        chain.doFilter(request, response);
        return;
       }

       String token = header.replace(PREFIX_TOKEN, "");

       try {
           Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
           String username = claims.getSubject();
        //    Object authoritiesClaims = claims.get("authorities");

        List<String> roles = (List<String>) claims.get("authorities");

        //    Collection <? extends GrantedAuthority> authorities = Arrays.asList(
        //     new ObjectMapper()
        //     .addMixIn(SimpleGrantedAuthority.class,SimpledGrantedAuthorityJsonCreator.class)
        //     .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

Collection<GrantedAuthority> authorities =
        roles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

           UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
       } catch (JwtException e) {
            Map<String,String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT es inválido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
            
       }
    }

}
