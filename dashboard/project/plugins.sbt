// The Play plugin
resolvers += Resolver.mavenLocal
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.15")

// Assembly (fat jar) plugin.
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")
