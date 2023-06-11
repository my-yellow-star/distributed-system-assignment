package assignment.chana.distributedsystem.file.infra

import assignment.chana.distributedsystem.file.FileRepository
import assignment.chana.distributedsystem.file.FileUploader
import assignment.chana.distributedsystem.file.UserFile
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.*

@Component
class LocalStorageFileUploader(
    private val fileRepository: FileRepository
) : FileUploader {
    override fun upload(userId: UUID, fileName: String, byteBuffer: ByteBuffer): UserFile {
        val file = File(resolveFilePath(userId), fileName)
        lateinit var out: FileOutputStream
        lateinit var outChannel: FileChannel
        try {
            byteBuffer.flip()
            out = FileOutputStream(file, true)
            outChannel = out.channel
            byteBuffer.compact()
            outChannel.write(byteBuffer)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out.close()
                outChannel.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        byteBuffer.position(0)
        return fileRepository.save(UserFile(userId, fileName, "$userId/$fileName"))
    }

    private fun resolveFilePath(userId: UUID): String {
        val path = "${FILE_UPLOAD_PATH}/src/main/resources/static/$userId"
        val file = File(path)
        if (!file.exists())
            file.mkdirs()
        return path
    }

    companion object {
        val FILE_UPLOAD_PATH: String = System.getProperty("user.dir")
    }
}