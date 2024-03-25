package kr.sometimesonline.rconapi.minecraft.controller;

import kr.sometimesonline.rconapi.common.rcon.vo.CommandRequestVo;
import kr.sometimesonline.rconapi.common.rcon.vo.MessageResponseVo;
import kr.sometimesonline.rconapi.common.rcon.vo.SocketCreateVo;
import kr.sometimesonline.rconapi.minecraft.service.MinecraftMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@MessageMapping("/minecraft") //  == "/app/palworld"
@SendToUser("/queue/response") // 메세지를 보낸 사람에게 리턴. 구독 경로는 /user/queue/response.
public class MinecraftMessageController {
    private final MinecraftMessageService messageService;

    @MessageMapping("/connect")
    public MessageResponseVo<String> connectRcon(SocketCreateVo socketCreateVO, SimpMessageHeaderAccessor headerAccessor) throws IOException {
        messageService.connectRcon(socketCreateVO, headerAccessor.getSessionId());
        return new MessageResponseVo<>(true, "Rcon Connected");
    }

    @MessageMapping("/command")
    public MessageResponseVo<String> executeCommand(CommandRequestVo command, SimpMessageHeaderAccessor headerAccessor) throws IOException {
        String responseMessage = messageService.executeCommand(command, headerAccessor.getSessionId());
        return new MessageResponseVo<>(true, responseMessage);
    }

    @MessageMapping("/disconnect")
    public MessageResponseVo<String> disconnectRcon(SimpMessageHeaderAccessor headerAccessor) throws IOException {
        messageService.disconnectRcon(headerAccessor.getSessionId());
        return new MessageResponseVo<>(true, "Rcon Disconnected");
    }

    @MessageExceptionHandler
    public MessageResponseVo<String> exceptionMessage(Exception exception) {
        log.error(exception.getMessage());
        return new MessageResponseVo<>(false, exception.getMessage());
    }
}
