package guice

import javax.inject.Inject

import com.google.inject.persist.PersistService

class JpaInitializer @Inject()(val service: PersistService) {
  service.start()
}
