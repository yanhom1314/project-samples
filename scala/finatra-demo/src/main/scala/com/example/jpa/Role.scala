package com.example.jpa

import java.util
import javax.persistence._

@Entity
@Table(name = "t_role")
case class Role(@Column var roleName: String) extends BaseEntity {
  @ManyToMany(cascade = Array(CascadeType.PERSIST))
  @JoinTable(name = "user_role", joinColumns = Array(new JoinColumn(name = "user_id", referencedColumnName = "id")),
    inverseJoinColumns = Array(new JoinColumn(name = "role_id", referencedColumnName = "id")))
  var users: util.List[LoginUser] = _
}
