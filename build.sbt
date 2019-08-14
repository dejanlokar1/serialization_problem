scalaVersion := "2.12.8"

val jacksonVersion = "2.9.9"

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
)
