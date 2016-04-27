package beans

import java.util

import scala.beans.BeanProperty

class Invoice {
  @BeanProperty
  var invoice: Integer = _
  @BeanProperty
  var date: String = _
  @BeanProperty
  var billTo: Person = _
  @BeanProperty
  var shipTo: Person = _
  @BeanProperty
  var product: util.List[Product] = _
  @BeanProperty
  var acmap: util.List[String] = _
  @BeanProperty
  var tax: Float = 0.0f
  @BeanProperty
  var total: Float = 0.0f
  @BeanProperty
  var comments: String = _
  @BeanProperty
  var post_url: String = _
}
