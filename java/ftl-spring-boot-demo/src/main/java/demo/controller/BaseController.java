package demo.controller;


import static demo.bean.ValContants.M_LOCALE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
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

//    protected void initBinder(HttpServletRequest request,
//                              ServletRequestDataBinder binder) {
//        binder.registerCustomEditor(Integer.class, null,new CustomNumberEditor(Integer.class, null, true));
//        binder.registerCustomEditor(Long.class, null,new CustomNumberEditor(Long.class, null, true));
//        binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
//        SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format", request.getLocale()));
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, null,new CustomDateEditor(dateFormat, true));
//    }
}
