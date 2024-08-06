package kr.sometimesonline.rconapi.user.controller

import kr.sometimesonline.rconapi.user.service.UserService
import kr.sometimesonline.rconapi.user.vo.UserResponseVo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @GetMapping("/{email}")
    fun getUserInfo(@PathVariable("email") email: String): UserResponseVo {
        return userService.getUserInfo(email)
    }
}
