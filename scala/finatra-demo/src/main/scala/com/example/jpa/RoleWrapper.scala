package com.example.jpa

object RoleWrapper {
  def apply(roleName: String): Role = {
    val role = new Role()
    role.setRoleName(roleName)
    role
  }
}

