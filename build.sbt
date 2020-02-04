name := "demo"
 
version := "1.0" 
      
lazy val `untitled` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Maven central" at "https://repo1.maven.org/maven2/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"       % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "3.4.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.1-scalikejdbc-3.4",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

enablePlugins(ScalikejdbcPlugin)


