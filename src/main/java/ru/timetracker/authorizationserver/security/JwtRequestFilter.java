package ru.timetracker.authorizationserver.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userId = null;
        String jwt = null;
        String tokenType = null;
        String subType = null;
        String deviceId = null;
        String customerId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                tokenType = jwtTokenUtils.getTokenType(jwt);
                if(tokenType.equals("access-token")){
                    userId = jwtTokenUtils.getUserId(jwt);
                    subType = jwtTokenUtils.getSubType(jwt);
                    customerId = jwtTokenUtils.getCustomerId(jwt);
                    if(subType.equals("device")){
                        deviceId = jwtTokenUtils.getDeviceId(jwt);
                    }
                }

            } catch (ExpiredJwtException e) {
                log.warn("Token timeout");
            } catch (Exception e) {
                log.warn("Wrong token");
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.getUserById(Long.valueOf(userId)); //Ban check
            if(user.getUserStatus()!= UserStatus.BANNED && user.getUserStatus()!= UserStatus.DELETED) {
                List<SimpleGrantedAuthority> authorities = getSimpleGrantedAuthoritiesByUserId(userId);
                UserDetails userDetails = new CustomUserDetails(userId, deviceId, subType, customerId, authorities);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
