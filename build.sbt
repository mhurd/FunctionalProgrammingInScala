name := "FunctionalProgrammingInScala"

version := "1.0"

scalaVersion := "2.11.0"

val scalazVersion = "7.0.6"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test",
  "org.scalatest" % "scalatest_2.11" % "2.1.7" % "test"
)

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"