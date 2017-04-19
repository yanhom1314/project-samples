package demo.http;

import demo.entity.repo.ClientRepository;
import demo.spring.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserCache userCache;

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return "redirect:/index" + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("all", userCache.getAll());
        return "index";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "name", defaultValue = "World") String name, Model model, HttpServletRequest request) {
        model.addAttribute("title", "What the fucking hell!");
        return "index";
    }

    @GetMapping("/access/{id}")
    public String access(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientRepository.findOne(id));
        return "access";
    }
}
