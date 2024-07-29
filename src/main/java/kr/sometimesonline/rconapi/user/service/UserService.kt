package kr.sometimesonline.rconapi.user.service

import kr.sometimesonline.rconapi.user.entity.UserEntity
import kr.sometimesonline.rconapi.user.repository.UserRepository
import kr.sometimesonline.rconapi.user.vo.UserResponseVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {
    fun getUserInfo(email: String): UserResponseVo {
        val userEntity: UserEntity = userRepository.findByEmail(email)
            ?: throw NoSuchElementException()
        return userEntity.toUserResponseVo()
    }
}
