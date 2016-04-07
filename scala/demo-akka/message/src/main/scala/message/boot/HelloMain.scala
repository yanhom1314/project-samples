package message.boot

import com.google.inject.Guice
import message.module.DemoModule
import message.service.{HiService, SayService}

/**
  * Created by LYF on 2016/3/28.
  */
object HelloMain extends App{
  println("Hello World!")
  val injector = Guice.createInjector(new  DemoModule)
  val say = injector.getInstance(classOf[SayService])
  val hi = injector.getInstance(classOf[HiService])


  println(s"say:${say} hi:$hi")
  say.say()
  hi.hi()
}
