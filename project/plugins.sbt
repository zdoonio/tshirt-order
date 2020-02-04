logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Maven central" at "https://repo1.maven.org/maven2/"

// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.4")
addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "3.2.1")
