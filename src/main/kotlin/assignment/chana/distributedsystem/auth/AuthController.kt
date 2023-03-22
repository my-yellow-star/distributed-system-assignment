package assignment.chana.distributedsystem.auth

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/register")
    fun getRegisterView(
        model: Model
    ): String {
        model.addAttribute("input", RegisterInput("", ""))
        return "contents/register"
    }

    @PostMapping("/register")
    fun register(
        @ModelAttribute("input") input: RegisterInput
    ): RedirectView {
        kotlin.runCatching {
            authService.register(input.username, input.password)
        }.getOrElse {
            it.printStackTrace()
            return RedirectView("register?error")
        }
        return RedirectView("login")
    }

    data class RegisterInput(
        val username: String,
        val password: String
    )
}