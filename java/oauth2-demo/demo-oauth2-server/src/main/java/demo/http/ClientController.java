package demo.http;

import demo.entity.Client;
import demo.oauth2.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client/**")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("clientList", clientService.findAll());
        return "client/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("op", "新增");
        return "client/edit";
    }

    @PostMapping("/create")
    public String create(Client client, RedirectAttributes redirectAttributes) {
        clientService.createClient(client);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/client";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("op", "修改");
        return "client/edit";
    }

    @PostMapping("/update/{id}")
    public String update(Client client, RedirectAttributes redirectAttributes) {
        clientService.updateClient(client);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/client";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("op", "删除");
        return "client/edit";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        clientService.deleteClient(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/client";
    }
}
