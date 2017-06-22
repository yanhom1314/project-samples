package demo.helloworld;

import act.Act;
import act.inject.DefaultValue;
import act.util.Output;
import org.osgl.mvc.annotation.GetAction;

import static act.controller.Controller.Util.render;

public class HelloWorldApp {

    @GetAction
    public void home(@DefaultValue("world") @Output String who) {
    }

    @GetAction("/bye")
    public String sayBye() {
        return "Bye!";
    }

    @GetAction("/sayHello")
    public void sayHello(@DefaultValue("World!") String who) {
        render(who);
    }

    public static void main(String[] args) throws Exception {
        Act.start("Hello World");
    }

}
