package com.example.jpa

import javax.persistence.{Column, Entity, Table}

@Entity
  @Table(name = "t_some_thing")
class SomeThing extends Thing {
  @Column
  var name: String = _
    @Column
  var flag: String = _
}

object SomeThing {
  def apply(id: Long, name: String, flag: String): SomeThing = {
    val thing = new SomeThing()
    thing.id = id
    thing.name = name
    thing.flag = flag
    thing
  }
}
