package controllers

import org.pac4j.core.authorization.generator.AuthorizationGenerator
import org.pac4j.http.profile.IpProfile

class RoleAdminAuthGenerator extends AuthorizationGenerator[IpProfile] {

  override def generate(profile: IpProfile): Unit = {
    profile.addRole("ROLE_ADMIN")
  }
}