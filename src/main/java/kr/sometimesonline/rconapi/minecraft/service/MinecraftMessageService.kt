package kr.sometimesonline.rconapi.minecraft.service

import kr.sometimesonline.rconapi.common.rcon.RconSocket
import kr.sometimesonline.rconapi.common.rcon.repository.RconSocketRepository
import kr.sometimesonline.rconapi.common.rcon.vo.CommandRequestVo
import kr.sometimesonline.rconapi.common.rcon.vo.SocketCreateVo
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class MinecraftMessageService(private val rconSocketRepository: RconSocketRepository) {
    @Throws(IOException::class)
    fun connectRcon(socketCreateVO: SocketCreateVo, sessionId: String) {
        val createdSocket = RconSocket(socketCreateVO.host, socketCreateVO.port, socketCreateVO.password)
        rconSocketRepository.putRconSocket(sessionId, createdSocket)
    }

    @Throws(IOException::class)
    fun executeCommand(commandRequestVo: CommandRequestVo, sessionId: String): String {
        val rconSocket = rconSocketRepository.getRconSocket(sessionId)

        rconSocket.executeCommand(commandRequestVo.command)
        val response = rconSocket.readResponse()

        return if (response.body.isEmpty()) "success" else response.body
    }

    @Throws(IOException::class)
    fun disconnectRcon(sessionId: String) {
        rconSocketRepository.disconnectRconSocket(sessionId)
    }
}
