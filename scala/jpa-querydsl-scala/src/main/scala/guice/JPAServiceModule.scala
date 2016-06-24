package guice

import com.google.inject.persist.jpa.JpaPersistModule
import com.google.inject.{AbstractModule, Scopes}
import repository.PersonRepository

class JPAServiceModule extends AbstractModule {
  override def configure(): Unit = {
    install(new JpaPersistModule("h2").properties(System.getProperties()))
    //Use @Singleton @ImplementBy instead of

    //bind(classOf[JpaInitializer]).asEagerSingleton()
    //bind(classOf[PersonRepository]).in(Scopes.SINGLETON)
  }
}
