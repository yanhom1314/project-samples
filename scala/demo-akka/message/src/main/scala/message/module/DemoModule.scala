package message.module

import com.google.inject.{Binder, Module}
import message.service.{HiService, HiServiceImpl, SayService}

/**
  * Created by LYF on 2016/3/28.
  */
class DemoModule extends Module {
  override def configure(binder: Binder): Unit = {
    binder.bind(classOf[SayService]).asEagerSingleton()
    binder.bind(classOf[HiService]).to(classOf[HiServiceImpl])
    println("module")
  }
}
