package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Home {
    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("title", "What the fucking hell!");
        return "index";
    }
}
