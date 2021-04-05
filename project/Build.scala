import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import sbtrelease.ReleasePlugin.autoImport._
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin
import xerial.sbt.Sonatype.autoImport._

object Build extends AutoPlugin {
  override def requires = JvmPlugin && HeaderPlugin

  override def trigger = allRequirements

  override def projectSettings = Vector(
    // Core settings
    publishTo := sonatypePublishToBundle.value,
    credentials := {
      val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
      val gpgCredentials = (baseDirectory in LocalRootProject).value / ".gpgCredentials"
      Credentials(ivyCredentials) :: Credentials(gpgCredentials) :: Nil
    },
    sonatypeProfileName := "com.chatwork",
    organization := "com.chatwork",
    organizationName := "Chatwork Co., Ltd.",
    startYear := Some(2021),
    licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
    headerLicense := Some(HeaderLicense.MIT("2021", "Chatwork Co., Ltd.")),
    homepage := Some(url("https://github.com/chatwork/healthchecks")),
    pomIncludeRepository := (_ => false),
    pomExtra := <scm>
      <url>https://github.com/chatwork/healthchecks</url>
      <connection>scm:git:git@github.com:chatwork/healthchecks</connection>
    </scm>
      <developers>
        <developer>
          <id>exoego</id>
          <name>TATSUNO Yasuhiro</name>
        </developer>
      </developers>,
    scalaVersion := Version.Scala.head,
    crossScalaVersions := Version.Scala,
    scalacOptions ++= Vector(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-encoding", "UTF-8",
    ) ++{
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n <= 12 =>
          Seq(
            "-Ywarn-unused",
            "-Ywarn-unused-import",
            "-Ywarn-adapted-args",
            "-Ywarn-inaccessible",
            "-Ywarn-infer-any",
            "-Ywarn-nullary-override",
            "-Ywarn-nullary-unit"
          )
        case _ =>
          Seq(
            "-Ymacro-annotations"
          )
      }
    },

    libraryDependencies ++= Def.setting(CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 12)) =>
        Seq(compilerPlugin(("org.scalamacros" % "paradise" % Version.paradise).cross(CrossVersion.patch)))
      case _                              =>
        Nil
    }).value,

    releaseCrossBuild := true,
    releaseVersionBump := sbtrelease.Version.Bump.Next
  )
}
