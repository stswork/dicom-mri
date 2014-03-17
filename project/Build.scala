import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "dicom-mri"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    cache,
    "com.typesafe" %% "play-plugins-mailer" % "2.2.0",
    "com.amazonaws" % "aws-java-sdk" % "1.7.3",
    "com.google.apis" % "google-api-services-storage" % "v1beta2-rev39-1.17.0-rc",
    "com.jolbox" % "bonecp" % "0.8.1-SNAPSHOT",
    "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "joda-time" % "joda-time" % "2.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers += "Sonatype OSS Snapshots Repository" at "http://oss.sonatype.org/content/groups/public"
  )

}
