package kr.sometimesonline.rconapi;

import kr.sometimesonline.rconapi.common.rcon.RconSocket;
import kr.sometimesonline.rconapi.common.rcon.RconMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RconCommunicationTests {

    @Test
    public void RconConnect() throws IOException {
        RconSocket rconSocket = new RconSocket("127.0.0.1", 25575, ""); // password 는 해당 서버의 비밀번호를 입력

        RconMessage request = rconSocket.executeCommand("player list"); // 임의 명령어 실행.
        RconMessage response = rconSocket.readResponse();  // 응답 확인.

        Assertions.assertThat(request.getRequestId()).isEqualTo(response.getRequestId());
    }
}
