package assignment.chana.distributedsystem.auth

import java.util.UUID
import org.springframework.stereotype.Component

@Component
class LocalUserRepository : UserRepository {
    private val data: MutableList<User> = mutableListOf()

    override fun save(user: User): User {
        if (findById(user.id) != null) {
            throw Exception("duplicated user id")
        }
        data.add(user)
        return user
    }

    override fun findById(id: UUID): User? =
        data.find { it.id == id }

    override fun findByName(name: String): User? =
        data.find { it.name == name }

    override fun findByNameAndPassword(name: String, password: String): User? =
        data.find { it.name == name && it.password == password }
}