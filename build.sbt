scalaVersion  := "2.11.7"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= {
  val akkaV  = "2.3.10"
  val sprayV = "1.3.3"
  Seq(
    "com.wandoulabs.akka" %%  "spray-websocket"       % "0.1.4"           withSources() withJavadoc,
    "io.spray"            %%  "spray-json"            % "1.3.1"           withSources() withJavadoc,
    "io.spray"            %%  "spray-can"             % sprayV            withSources() withJavadoc,
    "io.spray"            %%  "spray-routing"         % sprayV            withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-actor"            % akkaV             withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-slf4j"            % akkaV             withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-cluster"          % akkaV             withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-remote"           % akkaV             withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-contrib"          % akkaV             withSources() withJavadoc,
    "com.typesafe.akka"   %%  "akka-testkit"          % akkaV    % "test" withSources() withJavadoc,
    "io.spray"            %%  "spray-testkit"         % sprayV   % "test" withSources() withJavadoc,
    "org.scalatest"       %%  "scalatest"             % "2.2.4"  % "test",
    "junit"               %   "junit"                 % "4.12"   % "test",
    "org.specs2"          %%  "specs2"                % "2.4.17" % "test", 
    "ch.qos.logback"      %   "logback-classic"       % "1.1.3"
  )
}

