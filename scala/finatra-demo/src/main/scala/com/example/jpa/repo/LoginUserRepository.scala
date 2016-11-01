package com.example.jpa.repo

import com.example.jpa.LoginUser
import org.springframework.data.repository.CrudRepository

trait LoginUserRepository extends CrudRepository[LoginUser, java.lang.Long] {
  def findByUsername(username: String): LoginUser
}
