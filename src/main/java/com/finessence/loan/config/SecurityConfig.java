package com.finessence.loan.config;

import com.google.common.collect.ImmutableList;
import com.finessence.loan.config.security.RestUnauthorizedEntryPoint;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.finessence.loan"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class);
	public static final String REMEMBER_ME_KEY = "rememberme_key";
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("CONFIGURE GLOBAL ENTRY");
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	public SecurityConfig() {
		super();
	}

	@Autowired
	private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler restAccessDeniedHandler;

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//.headers().disable()
				.csrf().disable()
				.cors()
				.and()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/logout").permitAll()
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
//				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.accessDeniedHandler(restAccessDeniedHandler)
				.and()
				.formLogin()
				.loginProcessingUrl("/authenticate")
				.successHandler(restAuthenticationSuccessHandler)
				.failureHandler(restAuthenticationFailureHandler)
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
				.rememberMe()
				//.rememberMeServices(rememberMeServices)
				.key(REMEMBER_ME_KEY)
				.and();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(ImmutableList.of("*"));
		configuration.setAllowedMethods(ImmutableList.of("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
