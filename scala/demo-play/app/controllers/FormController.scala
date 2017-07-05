package controllers


import javax.inject._

import play.api.Logger
import play.api.mvc._
import play.filters.csrf._

@Singleton
class FormController @Inject()(addToken: CSRFAddToken, checkToken: CSRFCheck, cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  def addtoken = addToken {
    Action { implicit req: MessagesRequest[_] =>
      val token: CSRF.Token = CSRF.getToken.get
      Ok(
        s"""
           				|<!DOCTYPE html>
           				|<html><head></head><body>
           				|<form action="${routes.FormController.check}" method="post">
           				|	<input type="text" name="${token.name}" value="${token.value}" readonly="true" size="90">
           | <input type="text" name="name" value="${token.name}" size="90">
           				|	<button type="submit">Submit</button>
           				|</form>
           				|</body>
           				|""".stripMargin
      ).as("text/html")
    }
  }

  def notoken = Action { implicit req: MessagesRequest[_] =>
    Ok(
      s"""
         			|<!DOCTYPE html>
         			|<html><head></head><body>
         			|<form action="${routes.FormController.check}" method="post">
         |   <input type="text" name="host" value="${req.host}" size="90">
         			|   <button type="submit">Submit</button>
         			|</form>
         			|</body>
         			|""".stripMargin
    ).as("text/html")
  }

  def check = checkToken {
    Action { implicit req: RequestHeader =>
      Logger.debug("The check action has been executed")
      Ok("Success")
    }
  }

}

