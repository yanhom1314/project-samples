package demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

import demo.service.CaptchaFilter;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@SessionAttributes({CaptchaFilter.S_CAPTCHA_ID})
public class Captcha {

    private Producer producer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(props);
        this.producer = config.getProducerImpl();
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, Model model) throws Exception {
        ServletOutputStream out = response.getOutputStream();
        try {
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            String text = producer.createText();
            BufferedImage image = producer.createImage(text);
            ImageIO.write(image, "jpg", jpegOutputStream);

            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");

            model.addAttribute(CaptchaFilter.S_CAPTCHA_ID, text);
            out.write(jpegOutputStream.toByteArray());
        } finally {
            out.flush();
            out.close();
        }
    }
}