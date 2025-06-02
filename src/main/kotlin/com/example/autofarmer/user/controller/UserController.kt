package com.example.autofarmer.user.controller

import com.example.autofarmer.user.dto.UpdateRequest
import com.example.autofarmer.user.dto.UpdateResponse
import com.example.autofarmer.user.dto.UserDTO
import com.example.autofarmer.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    //전체 사용자 조회
    @GetMapping
    fun findAllUser(): ResponseEntity<List<UserDTO>> {
        val userList = userService.getAllUser()
        return ResponseEntity.ok(userList)
    }

    //사용자 정보 조회
    @GetMapping("/me")
    fun getUserInfo(): ResponseEntity<UserDTO?> {
        val user = userService.getUserInfo() ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "사용자가 존재하지 않습니다."
        )
        return ResponseEntity.ok(user)
    }

    //사용자 정보 수정
    @PatchMapping
    fun updateUser(@RequestBody request: UpdateRequest): ResponseEntity<UpdateResponse> {
        val updatedUser = userService.updateUserInfo(request)
        return ResponseEntity.ok(updatedUser)
    }
}
