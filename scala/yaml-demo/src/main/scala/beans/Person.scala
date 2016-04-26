package beans

import scala.beans.BeanProperty

class Person {
  @BeanProperty
  var given: String = _
  @BeanProperty
  var family: String = _
  @BeanProperty
  var address: Address = _
}
