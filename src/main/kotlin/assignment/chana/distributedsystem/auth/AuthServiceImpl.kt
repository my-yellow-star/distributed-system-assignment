package assignment.chana.distributedsystem.auth

import assignment.chana.distributedsystem.auth.exception.LoginFailedException
import assignment.chana.distributedsystem.auth.exception.RegisterFailedException
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class AuthServiceImpl(
    private val repository: UserRepository
) : AuthService {
    override fun register(username: String, password: String): UUID {
        if (repository.findByName(username) != null)
            throw RegisterFailedException("user already exists")
        val saved = repository.save(User(username, password))
        return saved.id
    }

    override fun login(username: String, password: String): UUID {
        val found = repository.findByNameAndPassword(username, password)
            ?: throw LoginFailedException()
        return found.id
    }
}