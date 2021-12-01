scalaVersion := "2.13.3"
name := "aoc-2020"
version := "1.0"

libraryDependencies += "com.lihaoyi"   %% "fastparse"  % "2.2.2"
libraryDependencies += "org.typelevel" %% "cats-core"  % "2.3.0"
libraryDependencies += "org.typelevel" %% "cats-parse" % "0.2.0"

addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.3.1")
