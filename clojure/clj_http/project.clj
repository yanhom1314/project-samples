(defproject clj_http "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"aliyun" "http://maven.aliyun.com/nexus/content/groups/public"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.netty/netty-all "4.0.31.Final"]
                 [http-kit "2.1.19"]]
  :main ^:skip-aot clj-http.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
