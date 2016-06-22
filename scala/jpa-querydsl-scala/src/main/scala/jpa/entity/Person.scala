package jpa.entity

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "t_person")
class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Long = _

  @Column
  @BeanProperty
  var name: String = _

  @Column
  @BeanProperty
  var age: Int = _
}
