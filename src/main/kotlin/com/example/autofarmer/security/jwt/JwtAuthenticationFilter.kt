package com.example.autofarmer.security.jwt

import com.example.autofarmer.user.domain.UserDetails
import com.example.autofarmer.user.service.UserDetailsService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
//Jwt 인증 필터
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            //요청에서 Authorization 헤더 가져오기
            val authHeader: String? = request.getHeader("Authorization")
            //Authorization 헤더가 null이거나 "Bearer "로 시작하지 않으면 필터 체인 진행
            if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }
            //헤더에서 JWT 토큰 추출
            val jwt = authHeader.substringAfter("Bearer ").trim()
            //토큰에서 사용자 번호 추출
            val userId = jwtService.extractClaims(jwt).subject.toLongOrNull()
            //인증되지 않은 사용자인 경우
            if (userId != null && SecurityContextHolder.getContext().authentication == null) {
                //사용자 번호로 사용자 정보 로드
                val foundUser: UserDetails = userDetailsService.loadUserByUserId(userId)
                //토큰이 유효한 경우
                if (jwtService.validateToken(jwt)) {
                    //인증 토큰 생성
                    val authToken = UsernamePasswordAuthenticationToken(
                        foundUser,
                        null,
                        foundUser.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    //SecurityContext에 인증 정보 설정
                    SecurityContextHolder.getContext().authentication = authToken
                }
                //이미 인증된 사용자라면 필터 체인 진행
                filterChain.doFilter(request, response)
                return
            }
        } catch (e: JwtException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
            return
        }
        filterChain.doFilter(request, response)
    }
}
