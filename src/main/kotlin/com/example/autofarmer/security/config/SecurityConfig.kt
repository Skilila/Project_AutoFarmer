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

// Spring Security 설정 클래스
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
            //인증 실패 시 처리
            .exceptionHandling { it ->
                it
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
            }
            // JWT 인증 필터 추가
            .addFilterBefore(
                JwtAuthenticationFilter(userDetailsService, jwtService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // JWT 예외 처리 필터 추가
            .addFilterBefore(
                JwtExceptionFilter(objectMapper),
                JwtAuthenticationFilter::class.java
            )
            .build()
    }

    // CORS 설정을 위한 빈
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000")// 허용할 오리진 설정
        configuration.allowedMethods = listOf("*")// 허용할 HTTP 메서드 설정
        configuration.allowedHeaders = listOf("*")// 허용할 헤더 설정
        configuration.allowCredentials = true// 자격 증명 허용 설정
        val source = UrlBasedCorsConfigurationSource()// CORS 설정을 적용할 URL 경로 설정
        source.registerCorsConfiguration("/**", configuration)// 모든 경로에 CORS 설정 적용
        return source
    }
}
