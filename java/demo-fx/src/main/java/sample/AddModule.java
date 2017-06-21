package sample;

import com.google.inject.AbstractModule;
import javafx.fxml.FXMLLoader;
import sample.guice.provider.FXMLLoaderProvider;

public class AddModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(ResourceBundleHelper.class);
        bind(Hello.class).to(HelloImpl.class);
    }
}
