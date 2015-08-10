package demo.web;

import com.jfinal.core.Controller;
import demo.bean.User;

import java.util.ArrayList;
import java.util.List;

public class HelloController extends Controller {
    public void index() {
        this.renderText("Hello World!");
    }

    public void hi() {
        render("/hi");
    }

    public void nice() {
        List<User> list = new ArrayList<>();
        list.add(new User(1L, "one"));
        list.add(new User(2L, "two"));
        list.add(new User(3L, "three"));
        list.add(new User(4L, "four"));
        setAttr("list", list);
        render("/nice");
    }
}
