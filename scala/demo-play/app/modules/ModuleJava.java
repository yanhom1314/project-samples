package modules;

import com.google.inject.AbstractModule;
import config.SpringDataJpaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.Repository;

import java.util.stream.Stream;

public class ModuleJava extends AbstractModule {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);

    @Override
    protected void configure() {
        System.out.println("conf/application.conf:[play.modules.enabled += \"modules.ModuleJava\".");
        Stream.of(ctx.getBeanNamesForType(Repository.class)).map(n -> ctx.getType(n)).forEach(c -> bind(c).toInstance(ctx.getBean(c)));
    }
}
