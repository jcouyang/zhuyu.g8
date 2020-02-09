import sbt._

object Dependencies {
  lazy val cats = Seq(
    "org.typelevel"   %% "cats-core" % "2.0.0-RC1",
    "org.typelevel" %% "cats-effect" % "1.2.0",
    "com.olegpy" %% "meow-mtl" % "0.2.0",
  )

  lazy val zhuyu = {
    val version = "0.4.0"
    Seq(
    "us.oyanglul" %% "zhuyu" % version,
    "us.oyanglul" %% "zhuyu-effect-http4s" % version,
    "us.oyanglul" %% "zhuyu-effect-s3" % version
  )
  }

  val doobieVersion = "0.6.0"
  lazy val doobie = Seq(
    "org.tpolecat" %% "doobie-core"      % doobieVersion,
    "org.tpolecat" %% "doobie-postgres"  % doobieVersion,
    "org.tpolecat" %% "doobie-hikari"    % doobieVersion
  )
  lazy val shapeless = Seq(
    "com.chuusai" %% "shapeless" % "2.3.3"
  )
  lazy val fs2 = Seq(
    "co.fs2" %% "fs2-io" % "1.0.4"
  )

  lazy val aws = Seq(
    "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.623",
    "com.amazonaws" % "aws-java-sdk-s3" % "1.11.623"
  )
  lazy val ciris = Seq(
    "is.cir"          %% "ciris-cats"          % "0.12.1",
    "is.cir"          %% "ciris-cats-effect"   % "0.12.1",
    "is.cir"          %% "ciris-core"          % "0.12.1",
    "is.cir"          %% "ciris-enumeratum"    % "0.12.1",
  )
  lazy val circe = Seq(
    "io.circe" %% "circe-core" % "0.11.1",
    "io.circe" %% "circe-generic" % "0.11.1",
    "io.circe" %% "circe-parser" % "0.11.1",
        "io.circe" %% "circe-literal" % "0.11.1" % Test
  )
  lazy val http4s = Seq(
    "org.http4s"      %% "http4s-blaze-client"          % "0.20.11",
  )

  lazy val specs2 = Seq(
    "org.specs2"      %% "specs2-core"         % "4.6.0" % "test",
    "org.specs2"      %% "specs2-scalacheck"   % "4.6.0" % "test",
    "org.specs2"      %% "specs2-mock"   % "4.6.0" % "test",
  )

  lazy val logback = Seq(
    "ch.qos.logback"  %  "logback-classic"     % "1.2.3",
  )

  lazy val scalacheckShapeless = Seq(
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.0" % "test"
  )

  lazy val scalacheck = Seq(
    "org.scalamock"   %% "scalamock" % "4.1.0" % "test",
  )
}
