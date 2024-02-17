package kr.sometimesonline.rconapi.user.controller;

import kr.sometimesonline.rconapi.user.service.UserService;
import kr.sometimesonline.rconapi.user.vo.UserResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // return 타입을 ResponseEntity<UserResponseVo> 로 변경 예정
    @GetMapping("/{email}")
    public UserResponseVo getUserInfo(@PathVariable("email") String email) {
        return userService.getUserInfo(email);
    }
}
