package assignment.chana.distributedsystem.file

import java.util.UUID

interface FileRepository {
    fun findById(id: UUID): UserFile?

    fun save(file: UserFile): UserFile

    fun findAllByUserId(userId: UUID): List<UserFile>

    fun delete(file: UserFile)
}