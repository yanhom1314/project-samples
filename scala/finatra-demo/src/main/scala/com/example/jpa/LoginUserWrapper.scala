package com.example.jpa

object LoginUserWrapper {
  def apply(username: String, password: String, age: Int, address: String): LoginUser = {
    val user = new LoginUser
    user.setUsername(username)
    user.setPassword(password)
    user.setAge(age)
    user.setAddress(address)
    user
  }
}