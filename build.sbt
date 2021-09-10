val commonSettings = Seq(
  scalaVersion := "2.13.5",
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0" % Test,
    "org.scalatestplus" %% "junit-4-13" % "3.2.9.0" % Test,
  )
)

val zioVersion = "1.0.11"
val zioDependencies = Seq(
  "dev.zio" %% "zio" % zioVersion,
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    publishArtifact := false,
  )
  .aggregate(core, doobieZio)

lazy val core = (project in file("iris"))
  .settings(commonSettings)
  .settings(
    name := "iris-core",
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-deprecation"
    ),
    libraryDependencies ++= Seq(
      scalaOrganization.value % "scala-reflect" % scalaVersion.value % "provided",
      scalaOrganization.value % "scala-compiler" % scalaVersion.value % "provided",
    )
  )

lazy val doobieZio = (project in file("iris-doobie-zio"))
  .settings(commonSettings)
  .settings(
    name := "iris-doobie-zio",
    libraryDependencies ++= zioDependencies,
    libraryDependencies ++= Seq(
      "io.zonky.test.postgres" % "embedded-postgres-binaries-bom" % "13.4.0" pomOnly()
    )
  )
  .aggregate(core)
  .dependsOn(core)
