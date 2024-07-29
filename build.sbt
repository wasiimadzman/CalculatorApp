name := "CalculatorApp"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.192-R14"
)

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-encoding", "utf8")
