package assignment.chana.distributedsystem.file

import java.util.UUID
import org.springframework.web.socket.WebSocketSession

data class UserSession(
    val userId: UUID,
    val session: WebSocketSession
)