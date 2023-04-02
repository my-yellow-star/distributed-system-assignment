package assignment.chana.distributedsystem.file.infra

import assignment.chana.distributedsystem.file.FileRepository
import assignment.chana.distributedsystem.file.UserFile
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalFileRepository : FileRepository {
    private val data: MutableList<UserFile> = mutableListOf()
    override fun save(file: UserFile): UserFile {
        if (data.any { it.id == file.id })
            throw Exception("duplicated file id")
        data.add(file)
        return file
    }

    override fun findAllByUserId(userId: UUID): List<UserFile> {
        return data.filter { it.userId == userId }
    }

    override fun delete(file: UserFile) {
        data.remove(file)
    }
}