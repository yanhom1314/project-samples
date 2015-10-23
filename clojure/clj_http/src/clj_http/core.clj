(ns clj-http.core
  (:gen-class)
  (:use org.httpkit.server))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello  HTTP!</h1>"})
(defn sat-hi [name]
  (println name))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-server app {:port 9002})
  (println "Listern to 127.0.0.1:9002, Server starting."))
