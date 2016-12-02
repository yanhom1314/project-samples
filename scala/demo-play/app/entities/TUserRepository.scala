package entities

import org.springframework.data.repository.CrudRepository

trait TUserRepository extends CrudRepository[TUser, java.lang.Long] {
  def findByUsername(username: String): TUser
}
