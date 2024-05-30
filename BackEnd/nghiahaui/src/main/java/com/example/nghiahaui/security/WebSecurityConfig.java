package com.example.nghiahaui.security;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.nghiahaui.security.jwt.AuthEntryPointJwt;
import com.example.nghiahaui.security.jwt.AuthTokenFilter;
import com.example.nghiahaui.security.service.UserDetailsServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
  
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
      return new AuthTokenFilter();
    }
  
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
     
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
    }
  
    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Cấu hình CORS
                .authorizeRequests(authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers("/api/auth/**").permitAll() // Cho phép truy cập đến các API liên quan đến xác thực
                                    .requestMatchers("/**").permitAll() // Cho phép truy cập vào tất cả các URL, bạn có thể thay đổi quyền truy cập tùy theo yêu cầu của bạn
                                    .anyRequest().authenticated();
                        } // Tất cả các yêu cầu cần được xác thực
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler) // Xử lý ngoại lệ xác thực
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Quản lý phiên, không lưu trữ trạng thái phiên
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider()); // Vô hiệu hóa CSRF

        // Cung cấp cung cấp xác thực cho HTTP Security

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Thêm bộ lọc JWT Token trước UsernamePasswordAuthenticationFilter

        return http.build(); // Trả về chuỗi bộ lọc bảo mật đã được cấu hình
    }


}
