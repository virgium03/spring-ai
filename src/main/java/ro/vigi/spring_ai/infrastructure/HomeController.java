package ro.vigi.spring_ai.infrastructure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author vigi
 */
@Controller
 class HomeController {

    @GetMapping("/")
    public String index() {
        return "welcome";
    }
}
