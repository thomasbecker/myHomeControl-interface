name := "myHomeControl-interface"
organization := "de.softwareschmied"
version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.mavenLocal

libraryDependencies += "de.softwareschmied" % "myHomeControl-client" % "1.0-SNAPSHOT"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"