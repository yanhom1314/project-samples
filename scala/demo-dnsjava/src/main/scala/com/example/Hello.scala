package com.example

import java.io.File

import org.xbill.DNS._

object Hello {
  val f_r = """([^s]+\.)hosts""".r

  def main(args: Array[String]): Unit = {
    println("Hello, world!")


    if (args.length >= 1) {
      /*
      val addr = Address.getByName("www.dnsjava.org")
      println(addr.getHostAddress + " " + addr.getHostName)

      new Lookup("gmail.com", Type.MX).run().filter(t => t.isInstanceOf[MXRecord]).map(_.asInstanceOf[MXRecord]).foreach { r =>
        println(s"Host ${r.getTarget}  has preference ${r.getPriority}")
      }
      */


      val fs = args(0)

      f_r.findFirstMatchIn(new File(fs).getName) match {
        case Some(mi) =>
          val m = new Master(fs, new Name(mi.group(1)))

          var r = m.nextRecord()
          while (r != null) {
            println(r)
            r = m.nextRecord()
          }
        case None =>
      }

      /*
      val xfr = ZoneTransferIn.newAXFR(new Name("."), "114.114.114.114", null)
      val records = xfr.run()
      records.foreach(println(_))
      */
    }
  }
}
