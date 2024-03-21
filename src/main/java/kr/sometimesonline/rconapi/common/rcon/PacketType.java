package kr.sometimesonline.rconapi.common.rcon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PacketType {
    SERVERDATA_AUTH(3),
    SERVERDATA_AUTH_RESPONSE(2),
    SERVERDATA_EXECCOMMAND(2),
    SERVERDATA_RESPONSE_VALUE(0);

    private final int typeValue;
}
