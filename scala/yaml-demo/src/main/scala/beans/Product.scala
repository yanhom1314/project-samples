package beans

import scala.beans.BeanProperty

class Product {
  @BeanProperty
  var sku: String = _
  @BeanProperty
  var quantity: Integer = _
  @BeanProperty
  var description: String = _
  @BeanProperty
  var price: Float = 0.0f
}
