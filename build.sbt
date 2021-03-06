val commonSettings = Seq(
  scalaVersion := "2.13.5",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.9.0" % Test,
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    publishArtifact := false,
  )
  .aggregate(core)

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

