package entities

import org.springframework.data.repository.CrudRepository

trait TAddressRepository extends CrudRepository[TAddress, java.lang.Long] {
  def findByHouseNumber(houseNumber: String): TAddress
}
