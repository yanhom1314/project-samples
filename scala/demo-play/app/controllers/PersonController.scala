package controllers

import javax.inject._

import models.PersonRepository
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class PersonController @Inject()(repo: PersonRepository)(implicit ec: ExecutionContext) extends InjectedController with I18nSupport {

  /**
    * The mapping for the person form.
    */
  val personForm: Form[CreatePersonForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "age" -> number.verifying(min(0), max(140))
    )(CreatePersonForm.apply)(CreatePersonForm.unapply)
  }

  /**
    * The index action.
    */
  def index = Action { implicit request: Request[AnyContent] =>
    request.session.data.toSeq.foreach(e => Logger.info(s"session: ${e._1}:${e._2}"))
    request.flash.data.toSeq.foreach(e => Logger.info(s"flash: ${e._1}:${e._2}"))
    Ok(views.html.person.index(personForm)).withSession(request.session + ("user" -> "YaFengLi"))
  }

  /**
    * The add person action.
    *
    * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
    */
  def addPerson = Action.async { implicit request: Request[AnyContent] =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    personForm.bindFromRequest.fold(
      // The error function. We return the index page with the error form, which will render the errors.
      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
      // a future because the person creation function returns a future.
      errorForm => {
        Future.successful(Ok(views.html.person.index(errorForm)))
      },
      // There were no errors in the from, so create the person.
      person => {
        repo.create(person.name, person.age).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.PersonController.index)
        }
      }
    )
  }

  /**
    * A REST endpoint that gets all the people as JSON.
    */
  def getPersons = Action.async {
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }
}

case class CreatePersonForm(name: String, age: Int)
