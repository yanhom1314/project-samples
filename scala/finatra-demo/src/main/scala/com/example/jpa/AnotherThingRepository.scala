package com.example.jpa

import org.springframework.data.repository.CrudRepository

trait AnotherThingRepository extends CrudRepository[AnotherThing, java.lang.Long] {
  def findByName(name: String): AnotherThing
}
