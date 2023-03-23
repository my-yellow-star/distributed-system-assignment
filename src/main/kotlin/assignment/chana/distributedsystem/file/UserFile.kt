package assignment.chana.distributedsystem.file

import java.nio.ByteBuffer
import java.util.UUID

data class UserFile(
    val userId: UUID,
    val fileName: String,
    val byteBuffer: ByteBuffer,
    val id: UUID = UUID.randomUUID()
)