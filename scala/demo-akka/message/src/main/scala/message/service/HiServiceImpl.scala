package message.service

import javax.inject.Inject

/**
  * Created by LYF on 2016/3/28.
  */
class HiServiceImpl @Inject()(val say: SayService) extends HiService {
  override def hi(): String = {
    println(s"say:$say")
    println("Hi Hi Hi!!!")
    System.currentTimeMillis().toString
  }
}
