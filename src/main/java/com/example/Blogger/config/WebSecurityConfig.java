package com.example.Blogger.config;

import com.example.Blogger.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired private JwtUserDetailsService userDetailsService;

    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired private JwtRequestFilter jwtRequestFilter;


    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
//    private static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
//    private static final String API_LOGIN = "/api/auth/**";
    private static final String GLOBAL_API = "/api/global/**";


    // configure Authentication Manager to know -
    // - where to load user matching credentials
    // - use BcryptPassword Encoder

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .antMatcher("/api/**")
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
//                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll()
                .antMatchers(GLOBAL_API).permitAll()
//                .antMatchers(API_LOGIN).permitAll()

                .and()
                .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()

                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
