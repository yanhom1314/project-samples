package finatra.auto

import java.nio.file.StandardWatchEventKinds.{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY}
import java.nio.file.{Files, Path, WatchEvent}
import java.util.concurrent.LinkedBlockingQueue

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Monitor {
  val queue = new LinkedBlockingQueue[Listener](1)

  def monitor(path: Path, listener: Listener): Future[Unit] = {
    Future {
      if (Files.exists(path) && Files.isDirectory(path)) {
        val watcher = path.getFileSystem.newWatchService()
        path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)
        var isRun = true
        while (isRun) {
          try {
            val key = watcher.take()
            println(s"key:${key}")
            if (queue.offer(listener)) {
              Future {
                key.pollEvents().asScala.foreach { event =>
                  val f = event.asInstanceOf[WatchEvent[Path]].context().toFile
                  if (f.isDirectory) listener.onDirectoryChanged(f)
                  else if (f.isFile) listener.onFileChanged(f)
                }
                Thread.sleep(3000)
                key.reset()
              } onComplete { case _ => queue.poll() }
            }
          } catch {
            case e: Exception =>
              e.printStackTrace()
              isRun = false
          }
        }
      }
    }
  }
}
