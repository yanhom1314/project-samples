package com.example.jpa

import javax.persistence.{Column, Entity, Table}

import scala.beans.BeanProperty

@Entity
@Table(name = "t_another_thing")
class AnotherThing extends BaseEntity {
  @BeanProperty
  @Column
  var name: String = _
  @BeanProperty
  @Column
  var flag: String = _
}

object AnotherThing {
  def apply(id: Long, name: String, flag: String): AnotherThing = {
    val thing = new AnotherThing()
    thing.id = id
    thing.name = name
    thing.flag = flag
    thing
  }
}