package entities

import java.util
import javax.persistence.{Column, _}

@Entity
@Table(name = "t_role")
class TRole extends TModel {
  @Column(name = "role_name", nullable = false, unique = true)
  var roleName: String = _

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
  var users: util.List[TUser] = _
}

object TRole {
  def apply(roleName: String): TRole = {
    val role = new TRole()
    role.roleName = roleName
    role
  }
}

