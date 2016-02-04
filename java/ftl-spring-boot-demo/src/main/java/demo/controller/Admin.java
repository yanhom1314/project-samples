package demo.controller;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
@RequestMapping("/admin/*")
public class Admin {
    @RequestMapping("index")
    public String index(HttpSession session, Model model) {
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + ":" + session.getAttribute(key));
        }
        SecurityContextImpl
        model.addAttribute("title", "What the fucking hell!");
        return "admin/index";
    }
}
