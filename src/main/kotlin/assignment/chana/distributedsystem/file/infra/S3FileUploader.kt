package assignment.chana.distributedsystem.file.infra

import assignment.chana.distributedsystem.file.FileRepository
import assignment.chana.distributedsystem.file.FileUploader
import assignment.chana.distributedsystem.file.UserFile
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.util.*

@Component
@Profile("develop")
class S3FileUploader(
    private val s3Client: AmazonS3Client,
    private val fileRepository: FileRepository
) : FileUploader {
    override fun upload(userId: UUID, fileName: String, byteBuffer: ByteBuffer): UserFile {
        val path = "$userId/$fileName"
        s3Client.putObject(
            PutObjectRequest(BUCKET_NAME, path, ByteBufferInputStream(byteBuffer), ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )
        return fileRepository.save(UserFile(userId, fileName, s3Client.getResourceUrl(BUCKET_NAME, path)))
    }

    companion object {
        const val BUCKET_NAME = "ku-public"
    }
}