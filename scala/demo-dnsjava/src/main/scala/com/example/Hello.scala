package com.example

import java.io.File

import com.google.common.net.InternetDomainName
import org.xbill.DNS._

import scala.collection.mutable

object Hello {
  val f_r = """([^\s]+\.)hosts""".r

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    if (args.length >= 1) {

      val fs = args(0)

      f_r.findFirstMatchIn(new File(fs).getName) match {
        case Some(mi) =>
          val m = new Master(fs, new Name(mi.group(1)))

          var r = m.nextRecord()
          while (r != null) {
            println(s"${r.getName}${tabPrint(r.getName.toString())}${r.getTTL}\t${DClass.string(r.getDClass)}\t${Type.string(r.getType)}\t${r.rdataToString()}")
            r = m.nextRecord()
          }
        case None =>
      }
    }
    //printDNS()
  }

  def printDNS(): Unit = {
    println("a.b.www.baidu.com. " + InternetDomainName.from("a.b.www.baidu.com.").isTopPrivateDomain.toString)
    println("mp3.baidu.com. " + InternetDomainName.from("mp3.baidu.com.").isTopPrivateDomain.toString)
    println("baidu.com. " + InternetDomainName.from("baidu.com.").isTopPrivateDomain.toString)


    println("a.b.www.baidu.com.cn. " + InternetDomainName.from("a.b.www.baidu.com.cn.").isTopPrivateDomain.toString)
    println("mp3.baidu.com.cn. " + InternetDomainName.from("mp3.baidu.com.cn.").isTopPrivateDomain.toString)
    println("baidu.com.cn. " + InternetDomainName.from("baidu.com.").isTopPrivateDomain.toString)

    println("a.b.www.baidu.xn--jlq61u9w7b. " + InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.").isTopPrivateDomain.toString)
    println("mp3.baidu.xn--jlq61u9w7b. " + InternetDomainName.from("mp3.baidu.xn--jlq61u9w7b.").isTopPrivateDomain.toString)
    println("baidu.xn--jlq61u9w7b. " + InternetDomainName.from("baidu.xn--jlq61u9w7b.").isTopPrivateDomain.toString)

    println("a.b.www.baidu.xn--jlq61u9w7b.cn. " + InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.cn.").isTopPrivateDomain.toString)
    println("mp3.baidu.xn--jlq61u9w7b.cn. " + InternetDomainName.from("mp3.baidu.xn--jlq61u9w7b.cn.").isTopPrivateDomain.toString)
    println("baidu.xn--jlq61u9w7b.cn. " + InternetDomainName.from("baidu.xn--jlq61u9w7b.cn.").isTopPrivateDomain.toString)
    println("xn--jlq61u9w7b.cn. " + InternetDomainName.from("xn--jlq61u9w7b.cn.").isTopPrivateDomain.toString)


    println(InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.cn.").parts())
    println(InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.cn.").publicSuffix())

    println(InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.").parts())
    println(InternetDomainName.from("a.b.www.baidu.xn--jlq61u9w7b.").publicSuffix())

    println(InternetDomainName.from("a.b.www.baidu.com.cn.").parts())
    println(InternetDomainName.from("a.b.www.baidu.com.cn.").publicSuffix())
    val name = Name.fromString("a.b.www.baidu.com.")

    println(name.isAbsolute)
    println("a.b.www.baidu.com.".split("\\.", -1).length)
    println(s"labels size:${name.labels()}")
    (0 until name.labels()).foreach(i => print(i + ":" + name.getLabelString(i) + " "))
    println()

    val buffer = mutable.ListBuffer[String]()

    domain("a.b.www.baidu.com.", buffer)
    buffer.reverse.foreach(println(_))
  }

  def domain(dn: String, buffer: mutable.ListBuffer[String]): Unit = {
    buffer += dn
    if (!InternetDomainName.from(dn).isTopPrivateDomain) domain(dn.substring(dn.indexOf(".") + 1), buffer)
  }


  def tabPrint(name: String): String = {
    val buffer = new StringBuffer("\t")
    if (name.length < 8) buffer.append("\t")
    if (name.length < 16) buffer.append("\t")
    if (name.length < 24) buffer.append("\t")
    if (name.length < 32) buffer.append("\t")
    buffer.toString
  }
}
