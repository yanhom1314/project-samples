package entities

import javax.persistence.{Entity, OneToOne, Table}

@Entity
@Table(name = "address")
class TAddress extends TModel {
  var houseNumber: String = _

  @OneToOne(mappedBy = "address")
  var person: TPerson = _
}
