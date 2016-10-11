package com.example

import java.io.File

import org.xbill.DNS._

object Hello {
  val f_r = """([^s]+\.)hosts""".r

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    if (args.length >= 1) {

      val fs = args(0)

      f_r.findFirstMatchIn(new File(fs).getName) match {
        case Some(mi) =>
          val m = new Master(fs, new Name(mi.group(1)))

          var r = m.nextRecord()
          while (r != null) {
            println(s"${r.getName}${if (r.getName.length() < 8) "\t\t\t" else if (r.getName.length() < 16) "\t\t" else "\t"}${r.getTTL}\t${DClass.string(r.getDClass)}\t${Type.string(r.getType)}\t${r.rdataToString()}")
            //(0 until (r.getName.labels() - 1)).foreach { i => print(s"${i} ${r.getName.getLabelString(i)} ") }
            //println()
            r = m.nextRecord()
          }
        case None =>
      }
    }
  }
}
