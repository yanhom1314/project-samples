package com.example.jpa

object AnotherThingWrapper {
  def apply(id: Long, name: String, flag: String): AnotherThing = {
    val thing = new AnotherThing()
    thing.id = id
    thing.setName(name)
    thing.setFlag(flag)
    thing
  }
}