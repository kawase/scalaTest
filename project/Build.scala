import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "todolist"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.25",
    "org.squeryl" %% "squeryl" % "0.9.5-6"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
