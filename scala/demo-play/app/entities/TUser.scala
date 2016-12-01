package entities

import java.util
import javax.persistence._

@Entity
@Table(name = "t_user")
class TUser extends TModel {
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
  var roles: util.List[TRole] = _
}