package com.greatbit.xgn.console.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StudentController {
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index() {
        return "form/index";
    }
    @RequestMapping("")
    public String i() {
        return "form/index";
    }
}
