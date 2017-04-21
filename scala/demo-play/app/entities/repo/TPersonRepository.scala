package entities.repo

import java.util

import entities.TPerson
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

trait TPersonRepository extends CrudRepository[TPerson, java.lang.Long] {
  def findByName(name: String): TPerson

  @Query(value = "select t from TPerson t where t.age >= ?1")
  def findAllByAge(age: Int): util.List[TPerson]
}
