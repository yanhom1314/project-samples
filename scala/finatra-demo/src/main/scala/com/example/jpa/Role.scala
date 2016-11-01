package com.example.jpa

import java.util
import javax.persistence.{Column, _}

@Entity
@Table(name = "t_role")
class Role extends BaseEntity {
  @Column(name = "role_name", nullable = false, unique = true)
  var roleName: String = _

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
  var users: util.List[LoginUser] = _
}

object Role {
  def apply(roleName: String): Role = {
    val role = new Role()
    role.roleName = roleName
    role
  }
}

