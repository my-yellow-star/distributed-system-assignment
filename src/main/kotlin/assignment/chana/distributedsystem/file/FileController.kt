package assignment.chana.distributedsystem.file

import assignment.chana.distributedsystem.auth.UserRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/files")
class FileController(
    private val userRepository: UserRepository
) {
    @GetMapping
    fun getFileView(model: Model, @AuthenticationPrincipal principal: User?) = run {
        if (principal == null)
            return@run RedirectView("/login")
        val user = userRepository.findByName(principal.username)
            ?: return@run RedirectView("/login")
        model.addAttribute("user", user)
        return@run "contents/file"
    }
}