package com.example.geowalk.security;

import com.example.geowalk.security.authorization.AuthenticationWithJsonFailureHandler;
import com.example.geowalk.security.authorization.AuthenticationWithJsonFilter;
import com.example.geowalk.security.authorization.AuthenticationWithJsonSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserPrincipalDetailsService userPrincipalDetailsService;
    private final AuthenticationWithJsonSuccessHandler successHandler;
    private final AuthenticationWithJsonFailureHandler failureHandler;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder,
                          UserPrincipalDetailsService userPrincipalDetailsService,
                          AuthenticationWithJsonSuccessHandler successHandler,
                          AuthenticationWithJsonFailureHandler failureHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/swagger-ui/#/*").permitAll()
                .and()
                .addFilterBefore(authenticationFilter(), AuthenticationWithJsonFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userPrincipalDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationWithJsonFilter authenticationFilter() throws Exception {
        AuthenticationWithJsonFilter filter = new AuthenticationWithJsonFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }
}
