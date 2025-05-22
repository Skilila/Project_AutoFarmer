package com.example.autofarmer.security.config

import com.example.autofarmer.security.handler.CustomAccessDeniedHandler
import com.example.autofarmer.security.handler.CustomAuthenticationEntryPoint
import com.example.autofarmer.security.jwt.JwtAuthenticationFilter
import com.example.autofarmer.security.jwt.JwtExceptionFilter
import com.example.autofarmer.security.jwt.JwtService
import com.example.autofarmer.user.service.UserDetailsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val objectMapper: ObjectMapper,
    private val jwtService: JwtService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val accessDeniedHandler: CustomAccessDeniedHandler
) {
    //PasswordEncoder를 빈으로 등록
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    //Http 요청에 대한 보안 설정을 구성하는 빈
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() } // CSRF 보호 비활성화
            .httpBasic { it.disable() } // HTTP Basic 인증 비활성화
            .cors { it.configurationSource(corsConfigurationSource()) } // CORS 설정
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }// 세션을 사용하지 않음
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/signup").permitAll()
                    .requestMatchers("/api/auth/verify-email").permitAll()
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/user/**").permitAll()
                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
            }
            .exceptionHandling { it ->
                it
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            }//인증 실패 시 처리
            .addFilterBefore(
                JwtAuthenticationFilter(userDetailsService, jwtService),
                UsernamePasswordAuthenticationFilter::class.java
            )// JWT 인증 필터 추가
            .addFilterBefore(
                JwtExceptionFilter(objectMapper),
                JwtAuthenticationFilter::class.java
            )// JWT 예외 처리 필터 추가
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
