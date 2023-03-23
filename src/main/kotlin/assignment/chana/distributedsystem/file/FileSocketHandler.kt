package assignment.chana.distributedsystem.file

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import java.util.*

class FileSocketHandler(
    private val fileUploader: FileUploader
) : BinaryWebSocketHandler() {
    private val sessions: MutableList<UserSession> = mutableListOf()
    private val fileUploadingSessions: MutableList<FileUploadingSession> = mutableListOf()

    companion object {
        const val START_FILE_UPLAOD_FLAG = "START"
        val FILE_UPLOAD_PATH: String = System.getProperty("user.dir")
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val tree = ObjectMapper().readTree(payload)
        when (tree.get("type").asText()) {
            START_FILE_UPLAOD_FLAG ->
                fileUploadingSessions
                    .add(FileUploadingSession(session.id, tree.get("fileName").asText()))
        }
    }

    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
        val userId = parseUserId(session)
        val uploadingSession =
            fileUploadingSessions.first { it.sessionId == session.id }
        val fileName = uploadingSession.fileName!!
        val byteBuffer = message.payload
        fileUploader.upload(userId, fileName, byteBuffer)
        val sessionsForSync =
            sessions.filter { it.userId == userId }
        sessionsForSync.forEach {
            runCatching {
                it.session.sendMessage(BinaryMessage(byteBuffer))
            }.getOrElse {
                it.printStackTrace()
            }
        }
        fileUploadingSessions.remove(uploadingSession)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        val userId = parseUserId(session)
        sessions.add(UserSession(userId, session))
        logger.info("connection established - id: ${session.id}")
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.removeAll { it.session.id == session.id }
        fileUploadingSessions.removeAll { it.sessionId == session.id }
        logger.info("connection closed - id: ${session.id}")
        super.afterConnectionClosed(session, status)
    }

    private fun parseUserId(session: WebSocketSession) =
        session.uri!!.query.split("&")
            .map {
                val parts = it.split("=")
                Pair(parts.firstOrNull() ?: "", parts.lastOrNull() ?: "")
            }.first {
                it.first == "userId"
            }.let {
                UUID.fromString(it.second)
            }
}