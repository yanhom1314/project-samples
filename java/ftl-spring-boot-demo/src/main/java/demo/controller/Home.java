package demo.controller;

import static demo.bean.ValContants.M_LOCALE;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class Home extends BaseController {
    @GetMapping("/")
    public String home(HttpServletRequest request) {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(@ModelAttribute(M_LOCALE) Locale locale, Model model) {
        model.addAttribute("title", messageSource.getMessage("logout", new Object[]{}, locale));
        return "index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user() {
        return "user/index";
    }
}
