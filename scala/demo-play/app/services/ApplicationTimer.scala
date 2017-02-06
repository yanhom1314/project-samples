package services

import java.time.{Clock, Instant}
import javax.inject._

import org.h2.tools.Server
import play.api.Logger
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class ApplicationTimer @Inject()(clock: Clock, appLifecycle: ApplicationLifecycle) {
  //H2 Database start
  //val server = Server.createTcpServer()

  // This code is called when the application starts.
  private val start: Instant = clock.instant
  Logger.info(s"ApplicationTimer demo: Starting application at ${start}.")

  // When the application starts, register a stop hook with the
  // Application Lifecyle object. The code inside the stop hook wil
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    //H2 Database stop
    //server.shutdown()

    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Logger.info(s"ApplicationTimer demo: Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }
}
