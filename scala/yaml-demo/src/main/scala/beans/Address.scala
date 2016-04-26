package beans

import scala.beans.BeanProperty

class Address {
  @BeanProperty
  var lines: String = _
  @BeanProperty
  var city: String = _
  @BeanProperty
  var state: String = _
  @BeanProperty
  var postal: String = _
}
