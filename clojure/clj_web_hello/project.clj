(defproject clj_web_hello "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring "1.4.0"]]
  :repositories [["osc" "http://maven.oschina.net/content/groups/public/"]]
  :plugins [[lein-ring "0.9.7"]]
  :plugin-repositories [["osc" "http://maven.oschina.net/content/groups/public/"]]
  :ring {:handler clj-web-hello.handler/app}
  :mirrors {"central" {:name "central"
                       :url "http://maven.oschina.net/content/groups/public"}
            #"clojars" {:name "Internal nexus"
                        :url "http://mvn.local/nexus/releases"
                        :repo-manager true}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.3.0"]]}})
