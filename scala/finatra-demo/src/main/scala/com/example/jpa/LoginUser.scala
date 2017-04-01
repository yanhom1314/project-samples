package com.example.jpa

import javax.persistence._

import scala.beans.BeanProperty
import scala.collection.mutable.ListBuffer

@Entity
@Table(name = "t_user")
class LoginUser extends BaseEntity {
  @BeanProperty
  @Column(nullable = false, unique = true)
  var username: String = _

  @BeanProperty
  @Column
  var password: String = _

  @BeanProperty
  @Column
  var age: Int = _

  @BeanProperty
  @Column
  var address: String = _

  @BeanProperty
  @ManyToMany(cascade = Array(CascadeType.MERGE), fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = Array(new JoinColumn(name = "user_id", referencedColumnName = "id")),
    inverseJoinColumns = Array(new JoinColumn(name = "role_id", referencedColumnName = "id")))
  var roles: ListBuffer[Role] = _
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