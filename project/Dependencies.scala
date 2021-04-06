import sbt._

object Version {
  val Scala = Seq("2.12.13", "2.13.5")
  val circe = "0.13.0"
  val akka = sys.env.getOrElse("AKKA_VERSION", "2.5.32")
  val akkaHttp = "10.2.4"
  val akkaHttpCirce = "1.36.0"
  val cats = "2.1.0"
  val scalaTest = "3.2.7"
  val paradise = "2.1.1"
}

object Dependencies {
  object cats {
    val core = "org.typelevel" %% "cats-core" % Version.cats
    val macros = "org.typelevel" %% "cats-macros" % Version.cats
    val kernel = "org.typelevel" %% "cats-kernel" % Version.cats
  }

  object akka {
    val actor = "com.typesafe.akka" %% "akka-actor" % Version.akka
    val stream = "com.typesafe.akka" %% "akka-stream" % Version.akka
    val http = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
    val httpCirce = "de.heikoseeberger" %% "akka-http-circe" % Version.akkaHttpCirce
    val testkit = "com.typesafe.akka" %% "akka-testkit" % Version.akka
    val streamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka
    val httpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp
  }

  val scalaTestFunspec = "org.scalatest" %% "scalatest-funspec" % Version.scalaTest
  val scalaTestMatchersCore = "org.scalatest" %% "scalatest-matchers-core" % Version.scalaTest

  object circe {
    val core = "io.circe" %% "circe-core" % Version.circe
    val generic = "io.circe" %% "circe-generic" % Version.circe
    val parser = "io.circe" %% "circe-parser" % Version.circe
  }
}
