package demo.controller;

import static demo.bean.ValContants.FORM_OBJECT_NAME;
import static demo.bean.ValContants.M_LOCALE;

import java.util.Collection;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.bean.Person;

@Controller
@RequestMapping("/person/**")
public class PersonController extends BaseController {
    public static final ConcurrentHashMap<Long, Person> persons = new ConcurrentHashMap<>();

    @ModelAttribute("list")
    public Collection<Person> list() {
        return persons.values();
    }

    @GetMapping("create")
    public String index(@ModelAttribute(FORM_OBJECT_NAME) Person person, @ModelAttribute(M_LOCALE) Locale locale, @ModelAttribute("list") Collection<Person> list) {
        return "admin/person";
    }

    @GetMapping("show/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Person person = persons.getOrDefault(id, new Person());
        model.addAttribute(FORM_OBJECT_NAME, person);
        return "admin/person";
    }

    @PostMapping("")
    public String post(@Valid @ModelAttribute(FORM_OBJECT_NAME) Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(t -> {
                System.out.println("name:" + t.getObjectName() + " code:" + t.getCode() + " message:" + t.getDefaultMessage());
            });
        } else {
            if (persons.containsKey(person.getId())) persons.remove(person.getId());
            person.setId(System.currentTimeMillis());
            persons.put(person.getId(), person);
        }
        return "admin/person";
    }

    @GetMapping("all")
    @ResponseBody
    public Collection<Person> all() {
        return list();
    }
}
