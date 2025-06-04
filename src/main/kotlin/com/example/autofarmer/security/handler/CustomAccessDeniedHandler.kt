package com.example.autofarmer.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver

//커스텀 권한 처리 핸들러
@Component
class CustomAccessDeniedHandler(
    @Qualifier("handlerExceptionResolver")
    private val resolver: HandlerExceptionResolver
) : AccessDeniedHandler {
    // AccessDeniedHandler 인터페이스를 구현하여 사용자가 권한이 없는 리소스에 접근할 때 호출되는 핸들러
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        resolver.resolveException(request, response, null, accessDeniedException)
    }
}
