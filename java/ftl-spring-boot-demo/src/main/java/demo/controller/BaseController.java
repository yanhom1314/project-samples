package demo.controller;


import static demo.bean.ValContants.M_LOCALE;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.LocaleResolver;

public abstract class BaseController {
    @Autowired
    protected LocaleResolver localeResolver;
    @Autowired
    protected MessageSource messageSource;

    @ModelAttribute(M_LOCALE)
    public Locale locale(HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        return locale;
    }
}
