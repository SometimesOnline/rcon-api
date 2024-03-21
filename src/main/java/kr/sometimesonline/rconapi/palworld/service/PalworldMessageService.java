package kr.sometimesonline.rconapi.palworld.service;

import kr.sometimesonline.rconapi.common.rcon.RconPacket;
import kr.sometimesonline.rconapi.common.rcon.RconSocket;
import kr.sometimesonline.rconapi.common.rcon.repository.RconSocketRepository;
import kr.sometimesonline.rconapi.common.rcon.vo.SocketCreateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PalworldMessageService {

    private final RconSocketRepository rconSocketRepository;

    public void connectRcon(SocketCreateVo socketCreateVO, String sessionId) throws IOException {
        RconSocket createdSocket = new RconSocket(socketCreateVO.host(), socketCreateVO.port(), socketCreateVO.password());
        rconSocketRepository.putRconSocket(sessionId, createdSocket);
    }

    public String executeCommand(String command, String sessionId) throws IOException {
        RconSocket rconSocket = rconSocketRepository.getRconSocket(sessionId);

        rconSocket.executeCommand(command);
        RconPacket response = rconSocket.readResponse();

        return response.getBody();
    }

    public void disconnectRcon(String sessionId) throws IOException {
        rconSocketRepository.getRconSocket(sessionId).disconnect();
    }
}
