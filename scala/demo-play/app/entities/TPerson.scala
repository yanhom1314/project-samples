package entities

import javax.persistence._

@Entity
@Table(name = "t_people")
class TPerson extends TModel {
  var name: String = _
  var age: Int = _

  @OneToOne(cascade = Array(CascadeType.MERGE))
  var address: TAddress = _
}
