package in.ali.citibank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.ali.citibank.exception.InvalidUserEntryPoint;
import in.ali.citibank.filter.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private InvalidUserEntryPoint invalidUserEntryPoint;
	
	@Autowired
	private SecurityFilter filter;
	
	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
			InvalidUserEntryPoint invalidUserEntryPoint) {
		
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.invalidUserEntryPoint = invalidUserEntryPoint;
	}

	
	

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService)
	  .passwordEncoder(bCryptPasswordEncoder);

	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.csrf().disable().authorizeRequests()
		.antMatchers("/user/save","/user/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(invalidUserEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		//TODO:verify user for 2nd request
         .and()
         //register the filter for 2nd request onwards
         .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		;
	}

}
