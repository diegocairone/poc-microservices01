package com.cairone;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class ResourceSecurityCfg extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        // https://is.eivsoftware.net:9443/oauth2/oidcdiscovery/.well-known/openid-configuration
        
        http
            .authorizeRequests()
                .antMatchers(
                    "/error/**",
                    "/actuator/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**", "/swagger-ui.html/**", "/swagger-resources/**"
                    )
                .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)
            .and()
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            ;
    }
    
}