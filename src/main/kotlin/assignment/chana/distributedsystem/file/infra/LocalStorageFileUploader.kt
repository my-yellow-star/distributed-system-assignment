package assignment.chana.distributedsystem.file.infra

import assignment.chana.distributedsystem.file.FileSocketHandler
import assignment.chana.distributedsystem.file.FileUploader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class LocalStorageFileUploader : FileUploader {
    override fun upload(userId: UUID, fileName: String, byteBuffer: ByteBuffer) {
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
    }

    private fun resolveFilePath(userId: UUID): String {
        val path = "${FileSocketHandler.FILE_UPLOAD_PATH}/src/main/resources/static/$userId}"
        val file = File(path)
        if (!file.exists())
            file.mkdirs()
        return path
    }
}