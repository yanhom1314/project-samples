package entities

import javax.persistence._

/**
  * This declares a model object for persistence usage. Model objects are generally anaemic structures that represent
  * the database entity. Behaviour associated with instances of a model class are also captured, but behaviours
  * associated with collections of these model objects belong to the PersonRepository e.g. findOne, findAll etc.
  * Play Java will synthesise getter and setter methods for us and therefore keep JPA happy (JPA expects them).
  */
@Entity
@Table(name = "t_people")
class TPerson extends TModel {
  var name: String = _
  var age: Int = _

  @OneToOne(cascade = Array(CascadeType.MERGE))
  var address: TAddress = _
}
