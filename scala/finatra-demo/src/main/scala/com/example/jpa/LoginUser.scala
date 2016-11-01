package com.example.jpa

import java.util
import javax.persistence._

@Entity
@Table(name = "t_user")
class LoginUser extends BaseEntity {
  @Column(nullable = false, unique = true)
  var username: String = _

  @Column
  var password: String = _

  @Column
  var age: Int = _

  @Column
  var address: String = _

  @ManyToMany(cascade = Array(CascadeType.MERGE), fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = Array(new JoinColumn(name = "user_id", referencedColumnName = "id")),
    inverseJoinColumns = Array(new JoinColumn(name = "role_id", referencedColumnName = "id")))
  var roles: util.List[Role] = _
}

object LoginUser {
  def apply(username: String, password: String, age: Int, address: String): LoginUser = {
    val user = new LoginUser
    user.username = username
    user.password = password
    user.age = age
    user.address = address
    user
  }
}