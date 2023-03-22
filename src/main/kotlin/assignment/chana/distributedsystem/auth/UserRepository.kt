package assignment.chana.distributedsystem.auth

import java.util.UUID

interface UserRepository {
    fun save(user: User): User
    fun findById(id: UUID): User?
    fun findByName(name: String): User?
    fun findByNameAndPassword(name: String, password: String): User?
}