package demo.dropwizard.views;

import demo.dropwizard.resources.Saying;
import io.dropwizard.views.View;

public class SayingView extends View {
    private final Saying saying;

    public SayingView(Saying saying) {
        super("saying.ftl");
        this.saying = saying;
    }

    public Saying getSaying() {
        return saying;
    }
}