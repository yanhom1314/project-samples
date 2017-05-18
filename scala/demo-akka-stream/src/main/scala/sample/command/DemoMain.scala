package sample.command

import com.beust.jcommander.{JCommander, Parameter}

object DemoOption {
  @Parameter(names = Array("-n", "--name"), description = "your name.", required = true)
  var name: String = _
  @Parameter(names = Array("-a", "--age"), description = "your age.")
  var age: Int = _
  @Parameter(names = Array("-h", "--help"), help = true)
  var help: Boolean = _
}

object DemoMain {
  def main(args: Array[String]) {
    var cmd: JCommander = null
    try {
      cmd = new JCommander(DemoOption, args: _*)
      if (DemoOption.help) cmd.usage()
      else {
        println(s"name:${DemoOption.name} age:${DemoOption.age} help:${DemoOption.help}")
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
