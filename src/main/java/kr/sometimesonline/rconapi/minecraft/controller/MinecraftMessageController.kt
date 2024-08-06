package kr.sometimesonline.rconapi.minecraft.controller

import kr.sometimesonline.rconapi.common.rcon.vo.CommandRequestVo
import kr.sometimesonline.rconapi.common.rcon.vo.MessageResponseVo
import kr.sometimesonline.rconapi.common.rcon.vo.SocketCreateVo
import kr.sometimesonline.rconapi.minecraft.service.MinecraftMessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import java.io.IOException

@Controller
@MessageMapping("/minecraft") //  == "/app/palworld"
@SendToUser("/queue/response") // 메세지를 보낸 사람에게 리턴. 구독 경로는 /user/queue/response.
class MinecraftMessageController(private val messageService: MinecraftMessageService) {
    @MessageMapping("/connect")
    @Throws(IOException::class)
    fun connectRcon(socketCreateVO: SocketCreateVo, headerAccessor: SimpMessageHeaderAccessor): MessageResponseVo<String> {
        messageService.connectRcon(socketCreateVO, headerAccessor.sessionId!!)
        return MessageResponseVo(true, "Rcon Connected")
    }

    @MessageMapping("/command")
    @Throws(IOException::class)
    fun executeCommand(command: CommandRequestVo, headerAccessor: SimpMessageHeaderAccessor): MessageResponseVo<String> {
        val responseMessage = messageService.executeCommand(command, headerAccessor.sessionId!!)
        return MessageResponseVo(true, responseMessage)
    }

    @MessageMapping("/disconnect")
    @Throws(IOException::class)
    fun disconnectRcon(headerAccessor: SimpMessageHeaderAccessor): MessageResponseVo<String> {
        messageService.disconnectRcon(headerAccessor.sessionId!!)
        return MessageResponseVo(true, "Rcon Disconnected")
    }

    @MessageExceptionHandler
    fun exceptionMessage(exception: Exception): MessageResponseVo<String?> {
        log.error(exception.message)
        return MessageResponseVo(false, exception.message)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MinecraftMessageController::class.java)
    }
}
