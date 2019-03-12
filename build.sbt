import sbt.Keys._

ThisBuild / scalaVersion := "2.12.4"
ThisBuild / organization := "xingu"
ThisBuild / name         := "xingu-scala-commons"
ThisBuild / version      := "v1.0-SNAPSHOT"

lazy val settings = Seq(
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val dependencies =
  new {
    val AkkaVersion = "2.5.16"
    val logback     = "ch.qos.logback" % "logback-classic" % "1.2.3"
    val scalaArm    = "com.jsuereth"   %% "scala-arm"      % "2.0"
    //val akka           = "com.typesafe.akka" %% "akka-actor" % AkkaVersion
}

lazy val commonDependencies = Seq()

lazy val commons = (project in file("commons"))
  .withId("xingu-scala-commons")
  .settings(settings)

lazy val logging = (project in file("logging"))
  .withId("xingu-scala-logging")
  .settings(
      settings, libraryDependencies ++= commonDependencies ++ Seq(dependencies.logback)
  )

lazy val play = (project in file("play"))
  .withId("xingu-scala-play")
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .dependsOn(commons)
  .settings(settings, libraryDependencies ++= Seq(dependencies.scalaArm))

lazy val root = (project in file("."))
    .aggregate(commons, logging, play)
    .settings(settings)
