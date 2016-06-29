import SonatypeKeys._

import sbt.Keys._

sonatypeSettings

name := "Type-safe and Scala-friendly library over Pdf.js"

normalizedName := "scala-js-pdf"

version := "0.0.1-SNAPSHOT"

organization := "io.surfkit"

scalaVersion := "2.11.8"

lazy val root = project.in(file(".")).
  enablePlugins(ScalaJSPlugin)

lazy val server = (project in file("server"))
  .settings(serverSettings:_*)

lazy val demo = (project in file("demo"))
  .settings(demoSettings:_*)
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(root)
  .aggregate(root)


lazy val serverSettings = Seq(
  name := s"server",
  scalaVersion := "2.11.8",
  libraryDependencies ++= {
    val akkaV       = "2.4.4"
    Seq(
      "com.typesafe.akka" %% "akka-actor"                         % akkaV,
      "com.typesafe.akka" %% "akka-stream"                        % akkaV,
      "com.typesafe.akka" %% "akka-http-experimental"             % akkaV,
      "com.typesafe.akka" %% "akka-http-spray-json-experimental"  % akkaV,
      "com.typesafe.akka" %% "akka-http-testkit"                  % akkaV
    )
  }
)


lazy val demoSettings = Seq(
  name := s"pdf-demo",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0"
    )
)

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.0"
)

jsDependencies in Test += RuntimeDOM

homepage := Some(url("http://www.surfkit.io/"))

licenses += ("MIT License", url("http://www.opensource.org/licenses/mit-license.php"))

scmInfo := Some(ScmInfo(
    url("https://github.com/coreyauger/scala-js-pdf"),
    "scm:git:git@github.com/coreyauger/scala-js-pdf.git",
    Some("scm:git:git@github.com:coreyauger/scala-jd-pdf.git")))

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <developers>
    <developer>
      <id>coreyauger</id>
      <name>Corey Auger</name>
      <url>https://github.com/coreyauger/</url>
    </developer>
  </developers>
)

pomIncludeRepository := { _ => false }

