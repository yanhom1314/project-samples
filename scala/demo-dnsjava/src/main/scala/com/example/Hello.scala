package com.example

import java.io.File

import com.google.common.net.InternetDomainName
import org.xbill.DNS._

import scala.collection.mutable

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
            (0 until (r.getName.labels() - 1)).foreach { i => print(s"${i} ${r.getName.getLabelString(i)} ") }
            println()
            r = m.nextRecord()
          }
        case None =>
      }
    }

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
    (1 to 2).foreach(i => try {
      println(name.wild(i).toString)
    } catch {
      case e: Exception => e.printStackTrace()
    })
  }

  def domain(dn: String, buffer: mutable.Buffer[String]): Unit = {
    buffer += dn
  }
}
