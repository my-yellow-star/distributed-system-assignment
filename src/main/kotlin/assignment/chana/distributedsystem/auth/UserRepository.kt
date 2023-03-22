package assignment.chana.distributedsystem.auth

import java.util.UUID

interface UserRepository {
    fun save(userEntity: UserEntity): UserEntity
    fun findById(id: UUID): UserEntity?
    fun findByName(name: String): UserEntity?
    fun findByNameAndPassword(name: String, password: String): UserEntity?
}