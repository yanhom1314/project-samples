package demo.http;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;

@Controller
public class SecureController {

    @RequiresAuthentication
    public String info() {
        return "admin/info";
    }
}
