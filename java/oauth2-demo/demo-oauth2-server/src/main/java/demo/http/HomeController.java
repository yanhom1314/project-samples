package demo.http;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return "redirect:/index?" + request.getQueryString();
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "name", defaultValue = "World") String name, Model model, HttpServletRequest request) {
        model.addAttribute("title", "What the fucking hell!");
        return "index";
    }

    @GetMapping("/access")
    public String access() {
        return "access";
    }
}
