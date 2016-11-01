package com.example.jpa.repo

import com.example.jpa.AnotherThing
import org.springframework.data.repository.CrudRepository

trait AnotherThingRepository extends CrudRepository[AnotherThing, java.lang.Long] {
  def findByName(name: String): AnotherThing
}
