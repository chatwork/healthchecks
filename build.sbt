lazy val root = project
  .in(file("."))
  .settings(
    name := "healthchecks",
    publish / skip := true,
    publishArtifact := false,
    publishLocal := {},
    publish := {}
  )
  .aggregate(core, k8sProbes)

lazy val core = project
  .enablePlugins(AutomateHeaderPlugin)
  .in(file("core"))

lazy val k8sProbes = project
  .in(file("k8s-probes"))
  .settings(
    name := "k8s-probes"
  )
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(core % "test->test;compile->compile")
