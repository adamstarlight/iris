val commonSettings = Seq(
  scalaVersion := "2.13.5",
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.9" % Test
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
    name := "iris-core"
  )

