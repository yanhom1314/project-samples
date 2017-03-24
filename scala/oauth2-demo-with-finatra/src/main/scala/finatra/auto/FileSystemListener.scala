package finatra.auto

import java.io.File

class FileSystemListener extends Listener {
  override def onDirectoryChanged(dir: File): Unit = {
    println("Directory " + dir.getAbsolutePath + " is changed.")
  }

  override def onFileChanged(file: File): Unit = {
    println("File " + file.getAbsolutePath + " is changed.")
  }
}
