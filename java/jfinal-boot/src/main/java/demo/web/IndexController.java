package demo.web;

import com.jfinal.core.Controller;

public class IndexController extends Controller {

    public void index() {
        this.render("index.html");
    }

    public void say() {
        renderText("Hello Index Page!");
    }
}
