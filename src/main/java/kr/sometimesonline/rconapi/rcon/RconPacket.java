package kr.sometimesonline.rconapi.rcon;

import lombok.Getter;
import lombok.ToString;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

@Getter
@ToString
public class RconPacket {

    private final int size;
    private final int requestId;
    private final int packetType;
    private final String body;


    public RconPacket(int requestId, int packetTypeValue, String body) {
        this.size = body.length() + 10; // requestId = 4 byte, packetType = 4 byte, body terminator(null) = 1 byte, packet terminator(null) = 1 byte
        this.requestId = requestId;
        this.packetType = packetTypeValue;
        this.body = body;
    }

    public RconPacket(int requestId, RconPacketType packetType, String body) {
        this(requestId, packetType.getTypeValue(), body);
    }

    public int getPacketSize() {
        return size + 4; // size 필드가 추가로 4 byte 사용.
    }

    public byte[] generatePacketBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(getPacketSize());
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(size);
        buffer.putInt(requestId);
        buffer.putInt(packetType);
        buffer.put(body.getBytes(StandardCharsets.US_ASCII));
        buffer.putShort((short) 0);

        return buffer.array();
    }
}
