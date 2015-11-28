package sample.asciidoc

import java.io.{File, PrintWriter}
import java.util.HashMap

import org.asciidoctor.Asciidoctor.Factory.create

import scala.io.Source

object MainDoc extends App {
  val ascii = create()
  if (args.length == 1) {
    println(s"#${args(0)}#")
    val html = ascii.convert(text(args(0)), new HashMap[String, Object]())
    printToFile("e:/tmp/lyf/doc.html")(_.write(html))
  }


  def printToFile(filename: String)(op: java.io.PrintWriter => Unit): Unit = {
    val p = new PrintWriter(new File(filename))
    try {
      op(p)
    } finally {
      p.close()
    }
  }

  def text(filename: String): String = {
    val buffer = new StringBuffer()
    Source.fromFile(new File(filename)).getLines().foreach { l => buffer.append(l); buffer.append(System.lineSeparator()) }
    buffer.toString
  }
}
