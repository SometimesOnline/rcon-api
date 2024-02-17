package kr.sometimesonline.rconapi.user.entity;

import jakarta.persistence.*;
import kr.sometimesonline.rconapi.user.vo.UserResponseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "EMAIL", length = 320, nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", length = 60, nullable = false) // 비밀번호 암호화 결과가 길이 60 고정임.
    private String password;

    public UserResponseVo toUserResponseVo() {
        return new UserResponseVo(this.userSeq, this.email);
    }
}


