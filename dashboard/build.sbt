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
libraryDependencies += javaJdbc
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
libraryDependencies += "edu.cmu.sei.ttg" % "kalki-db" % "1.6.0" exclude("commons-logging", "commons-logging")
libraryDependencies += "com.neuronrobotics" % "nrjavaserial" % "3.14.0"

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

assemblyMergeStrategy in assembly := {
  case manifest if manifest.contains("MANIFEST.MF") =>
    // We don't need manifest files since sbt-assembly will create
    // one with the given settings
    MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith ".conf" =>
    // Take last config file
    MergeStrategy.last
  case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
    // Keep the content for all reference-overrides.conf files
    MergeStrategy.concat
  case x =>
    // For all the other files, use the default sbt-assembly merge strategy
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
