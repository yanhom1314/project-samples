package com.greatbit.xgn.console.web;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;

@RestController
public class CaptchaController {
    public static final String SESSION_LOGIN_CAPTCHA_KEY = "s_login_captcha_key";
    private Config config;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("kaptcha.textproducer.char.length", "4");
        this.config = new Config(props);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpSession session, HttpServletResponse resp) {
        try {
            resp.setHeader("Cache-Control", "no-store, no-cache");
            resp.setContentType("image/jpeg");

            Producer producer = config.getProducerImpl();
            String captchaText = producer.createText();
            session.setAttribute(SESSION_LOGIN_CAPTCHA_KEY, captchaText);
            ImageIO.write(producer.createImage(captchaText), "jpg", resp.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
