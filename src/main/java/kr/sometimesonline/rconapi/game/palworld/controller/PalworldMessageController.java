package kr.sometimesonline.rconapi.game.palworld.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/palworld") //  == "/app/palworld"
@SendToUser("/queue/response") // 메세지를 보낸 사람에게 리턴. 구독 경로는 /user/queue/response.
public class PalworldMessageController {

    @MessageMapping("/connect")
    public String requestConnect(Message<String> message) {
        return "connect: " + message.getPayload();
    }

    @MessageMapping("/command")
    public String requestCommand(Message<String> message) {
        return "command: " + message.getPayload();
    }
}
