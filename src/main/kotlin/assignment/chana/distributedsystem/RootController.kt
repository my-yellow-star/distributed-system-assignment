package assignment.chana.distributedsystem

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping
class RootController {
    @GetMapping
    fun handleRoot(): RedirectView {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication is AnonymousAuthenticationToken)
            RedirectView("/login")
        else
            RedirectView("/files")
    }
}