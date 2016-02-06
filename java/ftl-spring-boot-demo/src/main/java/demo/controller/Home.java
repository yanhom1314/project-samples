package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Home {
    @RequestMapping("/index")
    public String index(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("title", "What the fucking hell!");
        return "index";
    }
}
