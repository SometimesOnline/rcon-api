package kr.sometimesonline.rconapi;

import kr.sometimesonline.rconapi.rcon.Rcon;
import kr.sometimesonline.rconapi.rcon.RconPacket;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RconCommunicationTests {

    @Test
    public void RconConnect() throws IOException {
        Rcon rcon = new Rcon("127.0.0.1", 25575, "c29f17a86f9bd2fe9e53dbd5"); // password 는 해당 서버의 비밀번호를 입력

        RconPacket request = rcon.executeCommand("player list"); // 임의 명령어 실행.
        RconPacket response = rcon.readResponse();  // 응답 확인.

        Assertions.assertThat(request.getRequestId()).isEqualTo(response.getRequestId());
    }
}
