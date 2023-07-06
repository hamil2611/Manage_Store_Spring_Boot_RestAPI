package com.example.managestore.configuration.securityConfig;

import com.example.managestore.configuration.jwt.JwtAuthenticationFilter;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
    @Value("${server.servlet.context-path}")
    private static String PATH;
    private static String[] AUTH_WHITELIST= {
            PATH
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/employee/**").hasAnyRole("OWNER","EMPLOYEE")
                        .requestMatchers("/user/**").hasRole("OWNER")
                        .requestMatchers("/schedule/**").hasRole("OWNER")
                        .requestMatchers("/category/**").hasRole("OWNER")
                        .requestMatchers("/clothes/**").hasAnyRole("OWNER","EMPLOYEE")
                        .requestMatchers("/shoes/**").hasAnyRole("OWNER","EMPLOYEE")
                        .requestMatchers("/orders/**").hasAnyRole("OWNER","EMPLOYEE")
                        .requestMatchers("/payslip/**").hasAnyRole("OWNER","EMPLOYEE")
                        .requestMatchers("/customer/**").hasAnyRole("OWNER","EMPLOYEE")
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin().disable()
                ;

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
