package guice

import javax.inject.{Inject, Singleton}

import com.google.inject.persist.PersistService

@Singleton
class JpaInitializer @Inject()(val service: PersistService) {
  service.start()
}
