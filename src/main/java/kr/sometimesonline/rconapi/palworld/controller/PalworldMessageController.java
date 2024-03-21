package kr.sometimesonline.rconapi.palworld.controller;

import kr.sometimesonline.rconapi.common.rcon.vo.SocketCreateVo;
import kr.sometimesonline.rconapi.palworld.service.PalworldMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.io.IOException;


//todo 리턴에 사용할 응답객체 만들기. ex) MessageResponseVo
@RequiredArgsConstructor
@Controller
@MessageMapping("/palworld") //  == "/app/palworld"
@SendToUser("/queue/response") // 메세지를 보낸 사람에게 리턴. 구독 경로는 /user/queue/response.
public class PalworldMessageController {

    private final PalworldMessageService messageService;

    @MessageMapping("/connect")
    public String connectRcon(SocketCreateVo socketCreateVO, SimpMessageHeaderAccessor headerAccessor) throws IOException {
        String sessionId = headerAccessor.getSessionId();
        messageService.connectRcon(socketCreateVO, sessionId);
        return "Rcon connected\n" + "WebSocket sessionId: " + sessionId;
    }

    @MessageMapping("/command")
    public String executeCommand(String command, SimpMessageHeaderAccessor headerAccessor) throws IOException {
        String responseMessage = messageService.executeCommand(command, headerAccessor.getSessionId());
        return "command: " + command + "\n response: " + responseMessage;
    }

    @MessageMapping("/disconnect")
    public String disconnectRcon(SimpMessageHeaderAccessor headerAccessor) throws IOException {
        messageService.disconnectRcon(headerAccessor.getSessionId());
        return "Rcon Disconnected";
    }
}
