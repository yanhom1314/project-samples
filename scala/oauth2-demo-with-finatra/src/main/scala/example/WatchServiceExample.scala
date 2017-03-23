package example

import java.io.IOException
import java.nio.file.StandardWatchEventKinds._
import java.nio.file._
import java.nio.file.attribute.BasicFileAttributes
import java.util

import scala.util.control.Breaks


object WatchServiceExample {
  @throws[IOException]
  def main(args: Array[String]): Unit = {
    val dir = Paths.get("c:/temp")
    new WatchServiceExample(dir).processEvents()
  }
}

class WatchServiceExample(val dir: Path) {
  final private var watcher = FileSystems.getDefault.newWatchService()
  final private var keys = new util.HashMap[WatchKey, Path]
  walkAndRegisterDirectories(dir)

  /**
    * Register the given directory with the WatchService; This function will be called by FileVisitor
    */
  @throws[IOException]
  private def registerDirectory(dir: Path) = {
    val key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
    keys.put(key, dir)
  }

  /**
    * Register the given directory, and all its sub-directories, with the WatchService.
    */
  @throws[IOException]
  private def walkAndRegisterDirectories(start: Path) = { // register directory and sub-directories
    Files.walkFileTree(start, new SimpleFileVisitor[Path]() {
      @throws[IOException]
      override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult = {
        registerDirectory(dir)
        FileVisitResult.CONTINUE
      }
    })
  }

  /**
    * Process all events for keys queued to the watcher
    */
  def processEvents(): Unit = {
    while ( {
      true
    }) { // wait for key to be signalled
      var key: WatchKey = null
      try
        key = watcher.take
      catch {
        case x: InterruptedException =>
          return
      }
      val dir = keys.get(key)
      if (dir == null) {
        System.err.println("WatchKey not recognized!!")

      }
      import scala.collection.JavaConverters._
      key.pollEvents().asScala.foreach { event =>
        @SuppressWarnings(Array("rawtypes")) val kind = event.kind
        // Context for directory entry event is the file name of entry
        @SuppressWarnings(Array("unchecked")) val name = event.asInstanceOf[WatchEvent[Path]].context
        val child = dir.resolve(name)
        // print out event
        System.out.format("%s: %s\n", event.kind.name, child)
        // if directory is created, and watching recursively, then register it and its sub-directories
        if (kind == ENTRY_CREATE) try
            if (Files.isDirectory(child)) walkAndRegisterDirectories(child)
        catch {
          case x: IOException => x.printStackTrace()
        }
      }

      // reset key and remove from set if directory no longer accessible
      val valid = key.reset
      if (!valid) {
        keys.remove(key)
        // all directories are inaccessible
        if (keys.isEmpty) Breaks.break()
      }
    }
  }
}