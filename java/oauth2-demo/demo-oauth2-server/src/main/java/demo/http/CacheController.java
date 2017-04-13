package demo.http;

import demo.spring.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CacheController {
    @Autowired
    private UserCache userCache;

    @GetMapping("/cache")
    public String index(Model model) {
        model.addAttribute("all", userCache.getAll());
        return "cache";
    }

    @GetMapping("/cache/update")
    public String refresh(Model model) {
        model.addAttribute("all", userCache.updateAll());
        return "cache";
    }
}
