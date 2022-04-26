package in.ali.citibank.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.ali.citibank.utility.JwtUtils;
import io.jsonwebtoken.Claims;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils utils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1.read the token from authorization header
		String token = request.getHeader("Authorization");
		if (token != null) {
			// do validation
			Claims claims = utils.getClaims(token);
			String username = claims.getSubject();

			// username shlould not be empty and contextHeader should be empty
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails user = userDetailsService.loadUserByUsername(username);

				// validate the token
				boolean isValid = utils.validateToken(token, user.getUsername());
				if (isValid) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							user.getPassword(), user.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// finally obj is stored in securitycontext with user detials
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);

	}

}
