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
val zioCatsVersion = "3.1.1.0"
val doobieVersion = "1.0.0-RC1"

val zioDependencies = Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-interop-cats" % zioCatsVersion,
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
      "org.tpolecat" %% "doobie-core"     % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "io.zonky.test.postgres" % "embedded-postgres-binaries-bom" % "13.4.0" % Test pomOnly(),
      "io.zonky.test" % "embedded-postgres" % "1.3.1" % Test,
      "org.slf4j" % "slf4j-api" % "1.7.32" % Test,
      "org.slf4j" % "slf4j-simple" % "1.7.32" % Test,
    )
  )
  .aggregate(core)
  .dependsOn(core)
