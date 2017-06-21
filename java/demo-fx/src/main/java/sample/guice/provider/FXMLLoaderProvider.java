package sample.guice.provider;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import sample.ResourceBundleHelper;

import javax.inject.Inject;
import javax.inject.Provider;

public class FXMLLoaderProvider implements Provider<FXMLLoader> {
    @Inject
    private Injector injector;
    @Inject
    private ResourceBundleHelper resourceBundleHelper;

    @Override
    public FXMLLoader get() {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resourceBundleHelper.resource());
        loader.setControllerFactory(p -> {
            System.out.println("p:" + p);
            return injector.getInstance(p);
        });
        return loader;
    }
}