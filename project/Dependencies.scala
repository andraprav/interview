import sbt._

object Dependencies {

  object Versions {
    val akka = "2.6.10"
    val cats = "2.3.0"
    val circe = "0.11.1"
    val enumeratum = "1.6.1"
    val pureConfig = "0.14.0"
    val spray = "1.3.6"

    // Runtime
    val logback = "1.2.3"

    // Test
    val quicklens = "1.6.1"
    val scalaCheck = "1.15.1"
    val scalaTest = "3.2.3"
    val scalaTestPlusScalaCheck = "3.2.2.0"
  }

  object Libraries {
    def akka(artifact: String): ModuleID = "com.typesafe.akka" %% artifact % Versions.akka

    lazy val akkaStream = akka("akka-stream")
    lazy val cats = "org.typelevel"               %% "cats-core"   % Versions.cats
    lazy val enumeratum = "com.beachape"          %% "enumeratum"  % Versions.enumeratum
    lazy val pureConfig = "com.github.pureconfig" %% "pureconfig"  % Versions.pureConfig
    lazy val spray =      "io.spray"              %%  "spray-json" % Versions.spray

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
    lazy val akkaStreamTestKit = akka("akka-stream-testkit")
    lazy val quicklens = "com.softwaremill.quicklens"      %% "quicklens"       % Versions.quicklens
    lazy val scalaCheck = "org.scalacheck"                 %% "scalacheck"      % Versions.scalaCheck
    lazy val scalaTest = "org.scalatest"                   %% "scalatest"       % Versions.scalaTest
    lazy val scalaTestPlusScalaCheck = "org.scalatestplus" %% "scalacheck-1-14" % Versions.scalaTestPlusScalaCheck
  }

}
