package kr.sometimesonline.rconapi.rcon;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

// todo
//  요청 응답 과정을 비동기식으로 변경.
//  커스텀 예외로 변경.

@Slf4j
public class Rcon {
    private final Random random = new Random();
    private final Socket socket;

    /**
     * 객체 생성시 소켓 연결 후 인증까지 시도함.
     * @param host 호스트(게임 서버) IP
     * @param port 포트
     * @param password Rcon 비밀번호
     * @throws IOException IOException
     */
    public Rcon(String host, int port, String password) throws IOException {
        this.socket = new Socket(host, port);

        RconPacket request = authenticate(password);
        RconPacket response = readResponse();

        if (response.getRequestId() == -1) {
            socket.close();
            throw new RuntimeException("password rejected"); // 임시로 만든 예외, 커스텀 예외로 변경 예정
        }

        assert request.getRequestId() == response.getRequestId();
    }

    /**
     * 게임 서버에 명렁어 전달
     * @param command 명령어
     * @return 서버에 전달한 값
     * @throws IOException IOException
     */
    public RconPacket executeCommand(String command) throws IOException {
        return sendPacket(RconPacketType.SERVERDATA_EXECCOMMAND, command);
    }

    /**
     * 게임 서버로부터 응답 확인
     * @return 서버로 부터 받은 값
     * @throws IOException IOException
     */
    public RconPacket readResponse() throws IOException {

        InputStream inputStream = socket.getInputStream();

        byte[] header = new byte[12];
        int readHeaderBytes = inputStream.read(header);
        if (readHeaderBytes != header.length) {
            throw new IOException("Incomplete header read");
        }

        ByteBuffer buffer = ByteBuffer.wrap(header);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        int size = buffer.getInt();
        int requestId = buffer.getInt();
        int type = buffer.getInt();

        byte[] bodyBytes = new byte[size - 10]; // requestId, type 이 각 4byte, 종료 신호의 null 두개가 각 1 byte
        int readBodyBytes = inputStream.read(bodyBytes);
        if (readBodyBytes != bodyBytes.length) {
            throw new IOException("Incomplete body read");
        }

        String body = new String(bodyBytes, StandardCharsets.US_ASCII);

        int readEndingBytes = inputStream.read(new byte[2]);
        if (readEndingBytes != 2) {
            throw new IOException("Incomplete ending read");
        }

        RconPacket response = new RconPacket(requestId, type, body);
        log.debug(String.format("rcon received response: %s", response));

        return response;
    }

    /**
     * 통신 종료.
     * @throws IOException IOException
     */
    public void disconnect() throws IOException {
        this.socket.close();
    }

    private RconPacket authenticate(String password) throws IOException {
        return sendPacket(RconPacketType.SERVERDATA_AUTH, password);
    }

    private RconPacket sendPacket(RconPacketType requestPacketType, String body) throws IOException {
        RconPacket rconPacket = new RconPacket(random.nextInt(), requestPacketType, body);
        log.debug(String.format("rcon sending request: %s", rconPacket));

        byte[] packetBytes = rconPacket.generatePacketBytes();
        OutputStream outputStream = this.socket.getOutputStream();
        outputStream.write(packetBytes);
        outputStream.flush();
        
        return rconPacket;
    }
}
