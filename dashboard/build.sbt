name := """dashboard"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
enablePlugins(DockerPlugin)

scalaVersion := "2.12.4"

//dockerExposedPorts in Docker := Seq(5432,9000,9443)

// Allows kalki-db dependency to be found
resolvers += Resolver.mavenLocal

routesGenerator := InjectedRoutesGenerator
libraryDependencies += guice
libraryDependencies += filters
libraryDependencies += javaJdbc
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.0"
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
libraryDependencies += "edu.cmu.sei.ttg" % "kalki-db" % "0.0.4-SNAPSHOT"

// https://mvnrepository.com/artifact/com.neuronrobotics/nrjavaserial
libraryDependencies += "com.neuronrobotics" % "nrjavaserial" % "3.14.0"
