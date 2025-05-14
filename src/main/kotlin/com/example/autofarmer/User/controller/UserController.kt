package com.example.autofarmer.User.controller

import com.example.autofarmer.User.dto.UserDTO
import com.example.autofarmer.User.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    //사용자 생성
    @PostMapping
    fun createUser(@Valid @RequestBody dto: UserDTO): ResponseEntity<UserDTO> {
        val createdUser = userService.createUser(dto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header(HttpHeaders.LOCATION, "/api/users/${createdUser.email}")
            .body(createdUser)
    }

    //특정 사용자 조회
    @GetMapping("/{userNo}")
    fun getUser(@PathVariable userNo: Long): ResponseEntity<UserDTO> {
        val user = userService.getUserByUserNo(userNo)
        return ResponseEntity.ok(user)
    }

    //사용자 전체 조회
    @GetMapping
    fun getAllUser(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUser()
        return ResponseEntity.ok(users)
    }

    //특정 사용자 수정
    @PutMapping("/{userNo}")
    fun updateUser(@PathVariable userNo: Long, @RequestBody newEmail: String, newNickname: String): ResponseEntity<UserDTO> =
        ResponseEntity.ok(userService.updateUser(userNo, newEmail, newNickname))

    //특정 사용자 삭제
    @DeleteMapping("/{userNo}")
    fun deleteUser(@PathVariable userNo: Long): ResponseEntity<Void> {
        userService.deleteUser(userNo)
        return ResponseEntity.noContent().build()
    }
}
