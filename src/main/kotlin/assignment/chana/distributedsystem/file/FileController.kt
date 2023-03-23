package assignment.chana.distributedsystem.file

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/files")
class FileController {
    @GetMapping
    fun getFileView(): String {
        return "contents/file"
    }
}