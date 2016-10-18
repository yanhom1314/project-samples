package entities

import javax.persistence.{GeneratedValue, GenerationType, Id, MappedSuperclass}

import scala.beans.BeanProperty

@MappedSuperclass
class TModel {
  @BeanProperty
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = _
}
