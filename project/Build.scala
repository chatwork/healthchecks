import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import sbtrelease.ReleasePlugin.autoImport._
import sbt.Keys._
import sbt.{AutoPlugin, _}
import sbt.plugins.JvmPlugin

object Build extends AutoPlugin {
  override def requires = JvmPlugin && HeaderPlugin

  override def trigger = allRequirements

  override def projectSettings = Vector(
    // Core settings
    organization := "com.github.everpeace",
    organizationName := "Shingo Omura",
    startYear := Some(2017),
    licenses += ("MIT", url("https://opensource.org/licenses/MIT")),
    headerLicense := Some(HeaderLicense.MIT("2017", "Shingo Omura")),
    homepage := Some(url("https://github.com/everpeace/healthchecks")),
    pomIncludeRepository := (_ => false),
    pomExtra := <scm>
      <url>https://github.com/everpeace/healthchecks</url>
      <connection>scm:git:git@github.com:everpeace/healthchecks</connection>
    </scm>
      <developers>
        <developer>
          <id>everpeace</id>
          <name>Shingo Omura</name>
          <url>http://everpeace.github.io/</url>
        </developer>
      </developers>,
    scalaVersion := Version.Scala.head,
    crossScalaVersions := Version.Scala,
    scalacOptions ++= Vector(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-encoding", "UTF-8",
      "-Ywarn-unused-import",
      "-Ypartial-unification"
    ),

    // Scalafmt setting
    scalafmtOnCompile := true,
    scalafmtTestOnCompile := true,

    // macro compiler plugin
    addCompilerPlugin(
      "org.scalamacros" % "paradise" % Version.paradise cross CrossVersion.full
    ),

    releaseCrossBuild := true,
    releaseVersionBump := sbtrelease.Version.Bump.Next
  )
}
