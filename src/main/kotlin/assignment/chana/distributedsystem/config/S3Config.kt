package assignment.chana.distributedsystem.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableConfigurationProperties(S3Properties::class)
@Profile("develop")
class S3Config(
    private val properties: S3Properties
) {
    private val credential: BasicAWSCredentials
        get() = BasicAWSCredentials(
            properties.accessKey,
            properties.secretKey
        )

    @Bean
    fun amazonS3Client(): AmazonS3Client =
        AmazonS3ClientBuilder
            .standard()
            .withRegion("ap-northeast-2")
            .withCredentials(AWSStaticCredentialsProvider(credential))
            .build() as AmazonS3Client
}


@ConfigurationProperties(prefix = "s3")
data class S3Properties @ConstructorBinding constructor(
    val accessKey: String,
    val secretKey: String
)