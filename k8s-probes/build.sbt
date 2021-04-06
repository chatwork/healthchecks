import Dependencies._

name := "healthchecks-k8s-probes"

libraryDependencies ++= Seq(
  akka.actor % Provided,
  akka.stream % Provided,
  akka.http % Provided,
  akka.httpCirce % Provided,
  akka.testkit % Test,
  akka.streamTestkit % Test,
  akka.httpTestKit % Test,
  scalaTest        % Test
)
