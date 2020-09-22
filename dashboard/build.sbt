name := """kalki-dashboard"""
organization := "edu.cmu.sei.ttg"

version := "1.6.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
enablePlugins(DockerPlugin)

scalaVersion := "2.12.4"

//dockerExposedPorts in Docker := Seq(5432,9000,9443)

// Allows kalki-db dependency to be found
resolvers += Resolver.mavenLocal

libraryDependencies += guice
libraryDependencies += filters
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
libraryDependencies += "edu.cmu.sei.ttg" % "kalki-db" % "1.6.0" exclude("commons-logging", "commons-logging")
libraryDependencies += "com.neuronrobotics" % "nrjavaserial" % "3.14.0"
