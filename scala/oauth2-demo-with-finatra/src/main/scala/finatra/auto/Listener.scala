package finatra.auto

import java.io.File

trait Listener {
  def onDirectoryChanged(dir: File): Unit

  def onFileChanged(file: File): Unit
}
