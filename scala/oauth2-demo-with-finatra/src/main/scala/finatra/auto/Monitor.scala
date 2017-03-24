package finatra.auto

import java.io.File
import java.nio.file.StandardWatchEventKinds.{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY}
import java.nio.file.{Files, Path, WatchEvent, WatchService}
import java.util.concurrent.LinkedBlockingQueue
import java.util.{Timer, TimerTask}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Monitor(path: Path) {
  private var isRun = true
  val queue = new LinkedBlockingQueue[(File, Listener)](1)
  lazy val watcher = path.getFileSystem.newWatchService()

  val timer = new Timer(true)

  def watch(listener: Listener): Unit = {
    if (Files.exists(path) && Files.isDirectory(path)) {
      path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)
      try {
        timer.scheduleAtFixedRate(new TimerTask {
          override def run(): Unit = {
            queue.poll() match {
              case (f, listener) =>
                if (f.isDirectory) listener.onDirectoryChanged(f)
                else if (f.isFile) listener.onFileChanged(f)
              case _ =>
            }
          }
        }, 1000, 2000)
        register(watcher, listener)
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
  }

  def terminate(): Unit = {
    isRun = false
    watcher.close()
    timer.cancel()
  }

  private def register(watcher: WatchService, listener: Listener): Unit = {
    Future {
      val key = watcher.take()
      val f = key.pollEvents().asScala.head.asInstanceOf[WatchEvent[Path]].context().toFile
      queue.offer(f -> listener)
      key.reset
      if (isRun) register(watcher, listener)
    }
  }
}
