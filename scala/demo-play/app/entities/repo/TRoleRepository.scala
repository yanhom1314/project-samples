package entities.repo

import entities.TRole
import org.springframework.data.repository.CrudRepository

trait TRoleRepository extends CrudRepository[TRole, java.lang.Long] {
}
