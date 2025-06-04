package com.example.autofarmer.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
//Jwt관련 예외 처리 필터
class JwtExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    // OncePerRequestFilter를 상속받아 요청당 한 번만 실행되는 필터
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)// 필터 체인 진행
        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED // 401 Unauthorized 상태 코드 설정
            response.contentType = "application/json" // 응답 타입을 JSON으로 설정
            response.characterEncoding = "UTF-8" // 응답 인코딩 설정
            mapOf("error" to e.message) // 예외 메시지를 포함한 JSON 응답 생성
            response.writer.write(objectMapper.writeValueAsString(response)) // ObjectMapper를 사용하여 JSON 문자열로 변환하여 응답에 작성
        }
    }
}
