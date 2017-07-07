package entities

import java.util
import javax.persistence._

@Entity
@Table(name = "t_user", indexes = Array(new Index(name = "i_username", columnList = "username", unique = true)))
class TUser extends TModel {
  @Column(name = "username", nullable = false)
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