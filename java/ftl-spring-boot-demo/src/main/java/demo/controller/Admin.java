package demo.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.bean.MyWebSocket;

@Controller
@RequestMapping("/admin/*")
public class Admin {

	@GetMapping("index")
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		Enumeration<String> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			System.out.println(key + ":" + session.getAttribute(key));
		}
		model.addAttribute("title", "What the fucking hell!");
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
