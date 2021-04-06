import Dependencies._

name := "healthchecks-core"

libraryDependencies ++= Seq(
  cats.core,
  cats.macros,
  cats.kernel,
  akka.actor % Provided,
  akka.stream % Provided,
  akka.http % Provided,
  akka.httpCirce % Provided,
  circe.core,
  circe.generic,
  circe.parser,
  akka.testkit % Test,
  akka.streamTestkit % Test,
  akka.httpTestKit % Test,
  scalaTestFunspec % Test,
  scalaTestMatchersCore % Test
)
