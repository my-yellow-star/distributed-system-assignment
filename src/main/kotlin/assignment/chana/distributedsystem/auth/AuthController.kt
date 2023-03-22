package assignment.chana.distributedsystem.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody body: RegisterInput) =
        authService
            .register(body.username, body.password)

    @PostMapping("/login")
    fun login(@RequestBody body: LoginInput) =
        authService
            .login(body.username, body.password)

    data class RegisterInput(
        val username: String,
        val password: String
    )

    data class LoginInput(
        val username: String,
        val password: String
    )
}