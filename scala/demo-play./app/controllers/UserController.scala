package controllers

import javax.inject.{Inject, Singleton}

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.mvc.{MessagesAbstractController, MessagesControllerComponents, MessagesRequest}

case class UserData(name: String, age: Int)

@Singleton
class UserController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  val userForm = Form(
    mapping(
      "name" -> text,
      "age" -> number
    )(UserData.apply)(UserData.unapply)
  )

  def userGet = Action { implicit request: MessagesRequest[_] =>
    Ok(views.html.form.user(userForm))
  }

  def userPost = Action { implicit request: MessagesRequest[_] =>
    println("######################")
    request.headers.toSimpleMap.foreach(t => println(s"${t._1}:${t._2}"))
    println("######################")

    userForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.form.user(formWithErrors))
      },
      userData => {
        /* binding success, you get the actual value. */
        /* flashing uses a short lived cookie */
        Redirect(routes.UserController.userGet()).flashing("success" -> ("Successful " + userData.toString))
      }
    )
  }
}
