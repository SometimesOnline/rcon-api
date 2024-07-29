package kr.sometimesonline.rconapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

// 스프링 시큐리티 임시 비활성화.
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class RconApiApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(RconApiApplication::class.java, *args)
        }
    }
}
