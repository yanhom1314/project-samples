package demo.controller;

import demo.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/form")
public class FormController extends BaseController {

    @GetMapping("/user")
    public String form(@ModelAttribute("object") User obj) {
        return "form/user";
    }

    @PostMapping("/user")
    public String form(@Valid @ModelAttribute("object") User obj, BindingResult bindingResult, Model model) {
        System.out.println("name:" + obj.getName() + " age:" + obj.getAge());
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(t -> {
                System.out.println("name:" + t.getObjectName() + " key:" + " code:" + t.getCode() + " message:" + t.getDefaultMessage());
            });
        } else {
            model.addAttribute("item", obj);
        }
        return "form/user";
    }
}
