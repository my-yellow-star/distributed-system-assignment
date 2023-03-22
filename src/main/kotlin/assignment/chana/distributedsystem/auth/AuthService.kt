package assignment.chana.distributedsystem.auth

import assignment.chana.distributedsystem.auth.exception.RegisterFailedException
import java.util.UUID
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

class AuthService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByName(username)
            ?: throw UsernameNotFoundException(username)
        return User(user.name, user.password, listOf(SimpleGrantedAuthority("user")))
    }

    fun register(username: String, password: String): UUID {
        if (username.length < 3)
            throw RegisterFailedException("username must contains at least 3 characters")
        if (repository.findByName(username) != null)
            throw RegisterFailedException("user already exists")
        val saved = repository.save(UserEntity(username, passwordEncoder.encode(password)))
        return saved.id
    }
}