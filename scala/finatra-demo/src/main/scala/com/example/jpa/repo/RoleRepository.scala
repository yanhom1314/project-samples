package com.example.jpa.repo

import com.example.jpa.Role
import org.springframework.data.repository.CrudRepository

trait RoleRepository extends CrudRepository[Role, java.lang.Long] {
  def findByRoleName(roleName: String): Role
}
