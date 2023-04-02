package assignment.chana.distributedsystem.file

import java.util.*

data class UserFile(
    val userId: UUID,
    val fileName: String,
    val path: String,
    val id: UUID = UUID.randomUUID()
) {
    val toMap: Map<String, String>
        get() = mapOf(
            "id" to id.toString(),
            "userId" to userId.toString(),
            "path" to path,
            "fileName" to fileName
        )
}