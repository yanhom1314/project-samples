resolvers ++= Seq("OSC Nexus" at "http://maven.oschina.net/content/groups/public/")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")

addSbtPlugin("org.koala" %% "sbt-application-plugin" % "1.0.0")

addSbtPlugin("org.netbeans.nbsbt" % "nbsbt-plugin" % "1.1.2")