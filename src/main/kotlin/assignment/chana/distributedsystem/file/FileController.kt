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
    private val userRepository: UserRepository,
    private val fileRepository: FileRepository
) {
    @GetMapping
    fun getFileView(model: Model, @AuthenticationPrincipal principal: User?) = run {
        if (principal == null)
            return@run RedirectView("/login")
        val user = userRepository.findByName(principal.username)
            ?: return@run RedirectView("/login")
        val files = fileRepository.findAllByUserId(user.id)
        model.addAttribute("user", user)
        model.addAttribute("files", files)
        return@run "contents/file"
    }
}