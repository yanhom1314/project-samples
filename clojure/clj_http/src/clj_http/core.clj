(ns clj-http.core
  (:gen-class)
  (:use org.httpkit.server))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello  HTTP!</h1>"})
(defn say-hi
  "This function is say Hi to your name!"
  [name]
  (println "Hi, " name))
(defn say-hello
  "This function is say Hello to your name!"
  [name]
  (println "Hello, " name "!")
  (println "Hello twice, " name ""))
(defn lyf-add
  "add a b"
  [a b]
  (+ a b))
(defn say-nice
  "This function is say Nice to your friends!"
  [name]
  (println "Nice, " name))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (say-hello "yafengli@sina.com")
  (say-hi "ya_feng_li@163.com")
  (say-hello "yafengli@sina.com")
  (say-hello (lyf-add 445 333))
  ;;(run-server app {:port 9002})
  (println "Listern to 127.0.0.1:9002, Server starting."))
