name := """dashboard"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

// Allows kalki-db dependency to be found
resolvers += Resolver.mavenLocal

libraryDependencies += guice
libraryDependencies += filters
libraryDependencies += javaJdbc
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
libraryDependencies += "edu.cmu.sei.ttg" % "kalki-db" % "0.0.4-SNAPSHOT"

// https://mvnrepository.com/artifact/com.neuronrobotics/nrjavaserial
libraryDependencies += "com.neuronrobotics" % "nrjavaserial" % "3.14.0"
