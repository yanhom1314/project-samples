package models

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

trait TPersonRepository extends CrudRepository[TPerson, java.lang.Long] {
  def findByName(name: String): TPerson

  @Query(value = "select count(*) from people", nativeQuery = true)
  def count(): Long
}
