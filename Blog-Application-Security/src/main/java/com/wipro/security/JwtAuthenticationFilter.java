package com.wipro.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	@Autowired
	private JWTHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String requestURI = request.getRequestURI();

	    // Bypass authentication filter for login and register endpoints
	    if (requestURI.contains("/auth/login") || requestURI.contains("/auth/register")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String requestHeader = request.getHeader("Authorization");
	    logger.info("Header : {}", requestHeader);

	    String username = null;
	    String token = null;

	    if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
	        logger.info("Invalid Header Value !!");
	        filterChain.doFilter(request, response);
	        return;
	    }

	    token = requestHeader.substring(7);
	    try {
	        username = this.jwtHelper.getUsernameFromToken(token);
	    } catch (IllegalArgumentException e) {
	        logger.error("Illegal Argument while fetching the username: {}", e.getMessage());
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	        return;
	    } catch (ExpiredJwtException e) {
	        logger.warn("JWT token expired: {}", e.getMessage());
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
	        return;
	    } catch (MalformedJwtException e) {
	        logger.error("Invalid JWT token: {}", e.getMessage());
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token Format");
	        return;
	    } catch (Exception e) {
	        logger.error("Unexpected error in JWT processing: {}", e.getMessage());
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
	        return;
	    }

	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	        if (this.jwtHelper.validateToken(token, userDetails)) {
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        } else {
	            logger.info("Validation fails !!");
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	            return;
	        }
	    }

	    filterChain.doFilter(request, response);
	}




//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		String requestHeader = request.getHeader("Authorization");
//		// Bearer 2352345235sdfrsfgsdfsdf
//		logger.info(" Header :  {}", requestHeader);
//		String username = null;
//		String token = null;
//		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
//			// looking good
//			token = requestHeader.substring(7);
//			try {
//
//				username = this.jwtHelper.getUsernameFromToken(token);
//
//			} catch (IllegalArgumentException e) {
//				logger.info("Illegal Argument while fetching the username !!");
//				e.printStackTrace();
//			} catch (ExpiredJwtException e) {
//				logger.info("Given jwt token is expired !!");
//				e.printStackTrace();
//			} catch (MalformedJwtException e) {
//				logger.info("Some changed has done in token !! Invalid Token");
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//
//		} else {
//			logger.info("Invalid Header Value !! ");
//		}
//
//		//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//			// fetch user detail from username
//			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
//			if (validateToken) {
//
//				// set the authentication
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//
//			} else {
//				logger.info("Validation fails !!");
//			}
//
//		}
//
//		filterChain.doFilter(request, response);
//	}

}