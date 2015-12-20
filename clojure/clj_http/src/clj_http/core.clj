(ns clj-http.core
  (:gen-class)
  (:use org.httpkit.server)
  (:use clj-http.lyf))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello  HTTP!</h1>"})
;;(defn say-hi
;;  "This function is say Hi to your name!"
;;  [name]
;;  (println "Hi, " name)
;;  (println "Hello, " name))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-server app {:port 9002})
  (say-hi "YaFengLi")
  (println "Listern to 127.0.0.1:9002, Server starting."))
