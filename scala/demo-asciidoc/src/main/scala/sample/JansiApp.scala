package scala.demo.main

import org.fusesource.jansi.Ansi.Color._
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.AnsiConsole

/**
 * @author ya_feng_li@163.com 
 * @version 0.0.1
 */
object JansiApp extends App {

  AnsiConsole.systemInstall()
  AnsiConsole.out().println("Hello World")
  AnsiConsole.out.println("@|red Hello|@ @|green World|@")

  AnsiConsole.systemUninstall()

  println(ansi().fg(RED).a("Hello").fg(GREEN).a(" World").reset())
  println(ansi().render("@|red Fuck|@ @|green YOU!!!|@"))
  println(ansi().render("@|yellow Fuck|@ @|cyan YOU!!!|@"))
  println(ansi().render("@|magenta Shit|@ @|cyan SHIT!!!|@"))

  println("=======================================================================")
  //  Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("/jansi.ans"),"ascii").foreach(System.out.write(_))
  println("=======================================================================")
}
