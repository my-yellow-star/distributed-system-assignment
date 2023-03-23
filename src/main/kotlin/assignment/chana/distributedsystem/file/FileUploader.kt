package assignment.chana.distributedsystem.file

import java.nio.ByteBuffer
import java.util.UUID

interface FileUploader {
    fun upload(userId: UUID, fileName: String, byteBuffer: ByteBuffer): UserFile
}