package assignment.chana.distributedsystem.auth.infra

import assignment.chana.distributedsystem.auth.UserEntity
import assignment.chana.distributedsystem.auth.UserRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class LocalUserRepository : UserRepository {
    private val data: MutableList<UserEntity> = mutableListOf()

    override fun save(userEntity: UserEntity): UserEntity {
        if (findById(userEntity.id) != null) {
            throw Exception("duplicated user id")
        }
        data.add(userEntity)
        return userEntity
    }

    override fun findById(id: UUID): UserEntity? =
        data.find { it.id == id }

    override fun findByName(name: String): UserEntity? =
        data.find { it.name == name }

    override fun findByNameAndPassword(name: String, password: String): UserEntity? =
        data.find { it.name == name && it.password == password }
}