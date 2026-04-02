package com.multigenesys.order_service.util;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authHeader = request.getHeader("Authorization");


		if (authHeader != null && authHeader.startsWith("Bearer ")) {

			String token = authHeader.substring(7);

			try {
				if (jwtUtil.isTokenValid(token)) {

					String username = jwtUtil.extractUsername(token);
					
					Long userId = jwtUtil.extractUserId(token);
					
					request.setAttribute("userId",userId);



					if (SecurityContextHolder.getContext().getAuthentication() == null) {

						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
								username, null, Collections.emptyList());

						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		filterChain.doFilter(request, response);
	}
}