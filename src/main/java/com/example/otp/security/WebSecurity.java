package com.example.otp.security;

import com.example.otp.services.OTPSendStrategy;
import com.example.otp.services.OTPService;
import com.example.otp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UsersService usersService;
    private Environment environment;
    private OTPService otpService;
    private OTPSendStrategy otpSendStrategy;

    @Autowired
    public WebSecurity(UsersService usersService, Environment environment, OTPService otpService, OTPSendStrategy otpSendStrategy) {
        this.usersService = usersService;
        this.environment = environment;
        this.otpService = otpService;
        this.otpSendStrategy = otpSendStrategy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/").permitAll()
                .and()
                .addFilter(getAuthFilter())
                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), environment), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

    private AuthenticationFilter getAuthFilter() throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(usersService, otpService, otpSendStrategy);
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
        return authFilter;
    }
}
