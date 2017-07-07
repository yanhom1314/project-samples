package security

import play.api.libs.json.{Format, Json}

case class SecuredProfile(name: String, roles: Iterable[String] = Seq[String]())

object SecuredProfile {
  val S_USERNAME = "s_username"
  implicit val format: Format[SecuredProfile] = Json.format[SecuredProfile]
}