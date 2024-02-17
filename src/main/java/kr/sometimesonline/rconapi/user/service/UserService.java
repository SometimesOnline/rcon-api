package kr.sometimesonline.rconapi.user.service;

import kr.sometimesonline.rconapi.user.entity.UserEntity;
import kr.sometimesonline.rconapi.user.repository.UserRepository;
import kr.sometimesonline.rconapi.user.vo.UserResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponseVo getUserInfo(String email) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        UserEntity userEntity = byEmail.orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return userEntity.toUserResponseVo();
    }
}
