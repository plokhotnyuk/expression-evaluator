import sbt._

lazy val commonSettings = Seq(
  organization := "com.github.plokhotnyuk.expression-evaluator",
  organizationHomepage := Some(url("https://github.com/plokhotnyuk")),
  homepage := Some(url("https://github.com/plokhotnyuk/expression-evaluator")),
  licenses := Seq(("Apache License 2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))),
  startYear := Some(2019),
  developers := List(
    Developer(
      id = "plokhotnyuk",
      name = "Andriy Plokhotnyuk",
      email = "plokhotnyuk@gmail.com",
      url = url("https://twitter.com/aplokhotnyuk")
    )
  ),
  resolvers += "Sonatype OSS Staging" at "https://oss.sonatype.org/content/repositories/staging",
  scalaVersion := "2.12.8",
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-target:jvm-1.8",
    "-feature",
    "-unchecked",
    "-Ywarn-dead-code",
    "-Xlint",
    "-Xmacro-settings:print-expr-results"
  ) ++ (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, x)) if x >= 12 => Seq(
      "-opt:l:method"
    )
    case Some((2, x)) if x == 11 => Seq(
      "-Ybackend:GenBCode",
      "-Ydelambdafy:inline"
    )
    case _ => Seq()
  }),
  testOptions in Test += Tests.Argument("-oDF")
)

lazy val publishSettings = Seq(
  publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging),
  sonatypeProfileName := "com.github.plokhotnyuk",
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/plokhotnyuk/expression-evaluator"),
      "scm:git@github.com:plokhotnyuk/expression-evaluator.git"
    )
  ),
  publishConfiguration := {
    val javaVersion = System.getProperty("java.specification.version")
    if (javaVersion != "1.8") throw new IllegalStateException("Cancelling publish, please use JDK 1.8")
    publishConfiguration.value
  },
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false }
)

lazy val `expression-evaluator` = project.in(file("."))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(publishSettings)
  .settings(
    crossScalaVersions := Seq("2.13.0", "2.12.8", "2.11.12"),
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )
