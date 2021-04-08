lazy val root = project
  .in(file("."))
  .settings(
    name := "scala2-introduction",
    organization := "io.scalac",
    scalaVersion := "2.13.5",
    scalacOptions := Seq(
      // https://docs.scala-lang.org/overviews/compiler-options/index.html#inner-main
      "-encoding",
      "UTF-8",
      "-deprecation",
      "-unchecked",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps",
      "-Xsource:2.13",
      // "-Xfatal-warnings",
      // "-Ywarn-dead-code",
      "-Ywarn-value-discard"
    ),
    libraryDependencies ++= Seq(
      // https://search.maven.org/search?q=g:dev.zio%20a:zio_2*
      // "dev.zio" % "zio_2.13"      % "1.0.5",
      "dev.zio"                %% "zio"                        % "1.0.5",
      "dev.zio"                %% "zio-streams"                % "1.0.5",
      "dev.zio"                %% "zio-test"                   % "1.0.5" % "test",
      "dev.zio"                %% "zio-test-sbt"               % "1.0.5" % "test",
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.2",
      "org.scala-lang"          % "scala-reflect"              % "2.13.5",
      "tech.units"              % "indriya"                    % "2.1.1",
      "org.scalactic"          %% "scalactic"                  % "3.2.7",
      "org.scalatest"          %% "scalatest"                  % "3.2.7" % "test"
    ),
    testFrameworks ++= Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt"),
    addCommandAlias(
      "check",
      "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
    )
  )
