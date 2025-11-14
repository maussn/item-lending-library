val Http4sVersion = "0.23.30"
val CirceVersion = "0.14.14"
val MunitVersion = "1.1.1"
val LogbackVersion = "1.5.18"
val MunitCatsEffectVersion = "2.1.0"
val SlickMySQLVersion = "8.0.33"

// Common settings for all subprojects
lazy val commonSettings = Seq(
  scalaVersion := "3.7.3",
  organization := "nl.sogyo",
  version := "0.1.0",
  assembly / assemblyMergeStrategy := {
    case "module-info.class" => MergeStrategy.discard
    case x => (assembly / assemblyMergeStrategy).value.apply(x)
  }
)

// Define the core project
lazy val ApiGateway = (project in file("rotom.api-gateway"))
  .settings(
    commonSettings,
    name := "api-gateway",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect"   % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion         % Runtime,
    )
  )

lazy val peristence = (project in file("rotom.persistence"))
  .settings(
    commonSettings,
    name := "persistence",
    libraryDependencies ++= Seq(
      "com.mysql" % "mysql-connector-j" % SlickMySQLVersion
    )
  )

// Define the app project, which depends on core
// lazy val AccountWriter = (project in file("account-writer"))
//     .settings(
//         commonSettings,
//         name := "account-writer"
//     )

lazy val root = (project in file("."))
  .aggregate(ApiGateway)
  .settings(
    name := "item-lending-library",
    commonSettings,
    publish/skip := true
  )