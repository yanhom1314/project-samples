package com.greatbit.xgn.console.web;

import com.greatbit.xgn.console.service.RedisDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private RedisDemo redisDemo;

    @RequestMapping("/index/{key}")
    public String index(@PathVariable("key") String key, Model model) {
        System.out.println("redisDemo:" + redisDemo);
        model.addAttribute("hello", redisDemo.get(key));
        return "demo/index";
    }

    @RequestMapping("/redis")
    public String redis(@RequestParam(value = "key") String key, Model model) {
        System.out.println("redisDemo:" + redisDemo);
        model.addAttribute("hello", redisDemo.hget(key));
        return "demo/index";
    }
}
