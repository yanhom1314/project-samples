package finatra.auto

import org.apache.commons.jci.listeners.FileChangeListener
import org.apache.commons.jci.monitor.FilesystemAlterationMonitor

object Hot {
  def listen(path: String)(restart: => Unit): Unit = {
    val listener = new Listener(path, restart)
    val fam = new FilesystemAlterationMonitor()
    fam.addListener(new java.io.File((new java.io.File(".").getCanonicalPath()) + "/" + path), listener)
    fam.start()
  }
}

class Listener(path: String, restart: => Unit) extends FileChangeListener {
  override def onDirectoryChange(pDir: java.io.File) {
    println("Content in " + pDir.getName() + " has changed ....")
  }

  override def onFileChange(pFile: java.io.File) {
    if (pFile.getName().endsWith(".scala")) {
      println("File: " + pFile.getName() + " has changed ....")
      println("Application is restarting ...")
      restart
    }
  }
}
