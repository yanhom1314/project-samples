package beans

import scala.beans.BeanProperty

/**
  * Created by YaFengLi on 2016/4/26.
  */
class Person {
  @BeanProperty
  var given: String = _
  @BeanProperty
  var family: String = _
  @BeanProperty
  var address: Address = _
}
