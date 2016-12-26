name := "slick-transactions"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.slick" % "slick_2.11" % "3.1.1",
  "com.h2database" % "h2" % "1.4.193"
)

libraryDependencies ++= Seq(
  "com.typesafe.slick" % "slick_2.11" % "3.1.1",
  "com.h2database" % "h2" % "1.4.193",
  "ch.qos.logback" % "logback-classic" % "1.1.8",
  "ch.qos.logback" % "logback-core" % "1.1.8"
)