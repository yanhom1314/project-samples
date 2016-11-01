package com.example.jpa

import java.util
import javax.persistence._


@Entity
@Table(name = "t_user")
class LoginUser extends BaseEntity {
  @Column
  var username: String = _

  @Column
  var password: String = _

  @Column
  var age: Int = _

  @Column
  var address: String = _

  @ManyToMany(mappedBy = "users")
  var roles: util.List[Role] = new util.ArrayList[Role]()
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