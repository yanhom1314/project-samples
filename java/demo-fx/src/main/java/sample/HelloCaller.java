package sample;

import javax.inject.Inject;

public class HelloCaller {
    @Inject
    private Hello hello;
    @Inject
    private ResourceBundleHelper resourceBundleHelper;

    public Hello getHello() {
        return hello;
    }

    public ResourceBundleHelper getResourceBundleHelper() {
        return resourceBundleHelper;
    }

    public void sayHello() {
        hello.sayHello();
    }
}