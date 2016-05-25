package sample.command

import com.beust.jcommander.{JCommander, Parameter}


object Option {
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
      cmd = new JCommander(Option, args: _*)
      cmd.usage()
      println("1:"+cmd.isParameterOverwritingAllowed)
      println(s"name:${Option.name} age:${Option.age}")
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
