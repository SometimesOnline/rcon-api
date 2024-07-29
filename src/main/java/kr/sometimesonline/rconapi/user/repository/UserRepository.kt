package kr.sometimesonline.rconapi.user.repository

import kr.sometimesonline.rconapi.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity?, Long?> {
    fun findByEmail(email: String): UserEntity?
}
