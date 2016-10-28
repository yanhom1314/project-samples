package com.example.jpa

import org.springframework.data.repository.CrudRepository

trait SomeThingRepository extends CrudRepository[SomeThing, java.lang.Long] {
  def findByName(name: String): SomeThing
}
