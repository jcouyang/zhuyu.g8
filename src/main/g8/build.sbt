import Dependencies._
import scala.util.Properties._

ThisBuild / scalaVersion     := "$scala_version$"
ThisBuild / organization     := "$organization$"
ThisBuild / organizationName := "$organization_name$"
ThisBuild / scalafmtOnCompile := true
ThisBuild / version          := envOrElse("APP_BUILD_NUMBER", "0.0.0-SNAPSHOT")
ThisBuild / publishTo := {
  val release = "$mvn_release$"
  if (version.value endsWith "-RC")
    Some("release-candidate" at release + "$mvn_release_candidate$")
  else
    Some("releases"  at release + "maven")
}
ThisBuild / pomIncludeRepository := { x => false }
$if(mvn_private.truthy)$
ThisBuild / credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
ThisBuild / resolvers += "private-maven" at "$mvn_private_repo$"
$endif$

lazy val models = project
  .settings(
    name := "$name;format="normalize"$-models",
    libraryDependencies ++= zhuyu
  )

lazy val root = (project in file("."))
  .settings(
    name := "$name;format="normalize"$",
    libraryDependencies ++=
      zhuyu ++
      http4s ++
      ciris ++
      circe ++
      logback ++
      scalacheckShapeless ++
      specs2 ++
      scalacheck,
    coverageMinimum := 100,
    coverageFailOnMinimum := true,
     coverageExcludedPackages := "*.impls.*;*.Main;*.models.*;"
  ).dependsOn(models)
