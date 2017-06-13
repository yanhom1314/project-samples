package demo.controller;

import demo.bean.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
@RequestMapping("/admin/*")
public class Admin {
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("index")
    public String index(HttpServletRequest request, HttpSession session, Model model) {
        Enumeration<String> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + ":" + session.getAttribute(key));
        }
        model.addAttribute("title", "What the fucking hell!");
        System.out.println("locale:" + localeResolver.resolveLocale(request));
        return "admin/index";
    }

    @GetMapping("ws/publish")
    @ResponseBody
    public Integer wsPub() {
        try {
            MyWebSocket.sendAllMessage("Hello " + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyWebSocket.getOnlineCount();
    }
}
