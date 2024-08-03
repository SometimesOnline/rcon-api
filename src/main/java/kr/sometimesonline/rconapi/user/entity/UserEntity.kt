package kr.sometimesonline.rconapi.user.entity

import jakarta.persistence.*
import kr.sometimesonline.rconapi.user.vo.UserResponseVo

// todo 코틀린에서의 JPA Entity 선언 방식 조금 더 고려 후 수정 ex) null
@Entity
@Table(name = "USER")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    val userSeq: Long? = null

    @Column(name = "EMAIL", length = 320, nullable = false, unique = true)
    val email: String? = null

    @Column(name = "PASSWORD", length = 60, nullable = false) // 비밀번호 암호화 결과가 길이 60 고정임.
    val password: String? = null

    fun toUserResponseVo(): UserResponseVo {
        userSeq ?: throw IllegalArgumentException("userSeq is null")
        email ?: throw IllegalArgumentException("email is null")
        return UserResponseVo(userSeq, email)
    }
}


