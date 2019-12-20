name := "myHomeControl-interface"
organization := "de.softwareschmied"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.7"

resolvers += Resolver.mavenLocal

libraryDependencies += "de.softwareschmied" % "myHomeControl-client" % "1.0-SNAPSHOT"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.0.0" % "test")
