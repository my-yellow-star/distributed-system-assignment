package assignment.chana.distributedsystem.config

import assignment.chana.distributedsystem.file.FileSocketHandler
import assignment.chana.distributedsystem.file.FileUploader
import assignment.chana.distributedsystem.file.infra.LocalStorageFileUploader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(socketHandler(), "/upload")
    }

    @Bean
    fun socketHandler(): WebSocketHandler {
        return FileSocketHandler(fileUploader())
    }

    @Bean
    fun fileUploader(): FileUploader {
        return LocalStorageFileUploader()
    }
}