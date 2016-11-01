package com.example.jpa

import javax.persistence.{Column, Entity, Table}

@Entity
@Table(name = "t_another_thing")
class AnotherThing extends BaseEntity {
  @Column
  var name: String = _
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