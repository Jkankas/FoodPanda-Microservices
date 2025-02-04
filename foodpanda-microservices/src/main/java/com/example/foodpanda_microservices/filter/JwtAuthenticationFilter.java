package com.example.foodpanda_microservices.filter;

import com.example.foodpanda_microservices.security.AdminUserDetailsService;
import com.example.foodpanda_microservices.util.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    AdminUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);
        String username = jwtUtility.getUsernameFromToken(token);

        if(username==null || SecurityContextHolder.getContext().getAuthentication()!=null ){
            filterChain.doFilter(request,response);
            return;
        }

        boolean isTokenValid = jwtUtility.validateToken(token);
        if(!isTokenValid){
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication;
        if(request.getRequestURI().contains("/api/customer/")){
            authentication = new UsernamePasswordAuthenticationToken(username,null,null);
        }
        else{
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        }
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}





//        String header = request.getHeader("Authorization");
//        if(header!=null && header.startsWith("Bearer ")){
//            String token = header.substring(7);
//            String username = jwtUtility.getUsernameFromToken(token);
//            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
//                if(request.getRequestURI().contains("/api/customer/") && jwtUtility.validateToken(token) ){
//                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,null);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                     }
//                else{
//                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                    if(jwtUtility.validateToken(token)){
//                        var authentication = new UsernamePasswordAuthenticationToken(userDetails,null,new ArrayList<>());
//                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                     }
//                 }
//            }
//        }
//        filterChain.doFilter(request,response);

