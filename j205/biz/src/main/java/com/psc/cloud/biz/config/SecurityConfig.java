package com.psc.cloud.biz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorize ->
				authorize
				.antMatchers("/api/profile").permitAll()
				.antMatchers("/api/client_a").hasAnyAuthority("SCOPE_CLIENT_A")
				.antMatchers("/api/client_b").hasAnyAuthority("SCOPE_CLIENT_B")
				.anyRequest().authenticated()
		)
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.exceptionHandling()
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					System.out.println("accessDeniedException.getMessage() = " + accessDeniedException.getMessage());
				});
	}
}
