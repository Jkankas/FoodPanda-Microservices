package com.example.foodpanda_microservices_warehouse.filter;

import com.example.foodpanda_microservices_warehouse.security.UserDetailsImplementation;
import com.example.foodpanda_microservices_warehouse.security.WarehouseUserDetailsService;
import com.example.foodpanda_microservices_warehouse.utility.JwtUtil;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WarehouseUserDetailsService userDetailsService;

    @Autowired
    private IpRateFilter rateFilter;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest req = request;
        HttpServletResponse res = response;

        String ip = req.getRemoteAddr();
        String path = req.getRequestURI();

        if(path.contains("/api/warehouse/getPriceByDishV1")){
            if(rateFilter.getBlacklistedIps().contains(ip)){
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("Access Blocked: BlackListed IP");
                return;
            }

            AtomicInteger counter = rateFilter.getHitCounter().computeIfAbsent(ip, new Function<String, AtomicInteger>() {
                @Override
                public AtomicInteger apply(String k) {
                    return new AtomicInteger(0);
                }
            });

            int hits = counter.incrementAndGet();
            if(hits>rateFilter.getBlacklistThreshold()){
                rateFilter.getBlacklistedIps().add(ip);
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("IP Temporarily blocked due to too many requests!");
                return;
            }

            Bucket bucket = rateFilter.getIpBuckets().computeIfAbsent(ip,k-> rateFilter.getNewBucket());
            if(!bucket.tryConsume(1)){
                res.setStatus(429);
                res.getWriter().write("Too many Request!, Please slow down");
                return;
            }
        }

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String email = jwtUtil.getUsernameFromToken(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(token)) {
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
