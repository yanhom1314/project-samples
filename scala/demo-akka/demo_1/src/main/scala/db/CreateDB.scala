package db

import slick.codegen.SourceCodeGenerator

object CreateDB extends App {


  println("###########Slick#########")
  SourceCodeGenerator.main(
    Array("slick.driver.PostgresDriver", DBContants.driver,  DBContants.url, "demo_1/src/main/scala", "db", "postgres", "")
  )
  println("###########Slick#########")
}
