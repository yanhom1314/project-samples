package com.example.jpa

import javax.persistence.{Column, _}

import scala.beans.BeanProperty

@Entity
@Table(name = "t_role")
class Role extends BaseEntity {
  @BeanProperty
  @Column(name = "role_name", nullable = false, unique = true)
  var roleName: String = _

  @BeanProperty
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
  var users: List[LoginUser] = _
}

object Role {
  def apply(roleName: String): Role = {
    val role = new Role()
    role.roleName = roleName
    role
  }
}

