package kr.sometimesonline.rconapi.common.rcon.repository;

import kr.sometimesonline.rconapi.common.rcon.RconSocket;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RconSocketRepository {
    private final Map<String, RconSocket> socketRepo = new ConcurrentHashMap<>();

    public void putRconSocket(String sessionId, RconSocket rconSocket) {
        socketRepo.put(sessionId, rconSocket);
    }

    public RconSocket getRconSocket(String sessionId) {
        return Optional.ofNullable(socketRepo.get(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Session Id"));
    }
}
