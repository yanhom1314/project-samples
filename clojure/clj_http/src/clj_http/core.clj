(ns clj-http.core
  (:gen-class)
  (:use clj-http.lyf)
  (:use org.httpkit.server))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello  HTTP!</h1>"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (say-nice "yafengli.dream@gmail.com")
  (say-hello "yafengli@sina.com")
  (say-hi "ya_feng_li@163.com")
  (say-hello "yafengli@sina.com")
  (say-hello (lyf-add 445 333))
  ;;(run-server app {:port 9002})
  (println "Listern to 127.0.0.1:9002, Server starting."))
