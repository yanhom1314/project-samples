(ns clj-http.core
  (:gen-class)
  (:use org.httpkit.server))


(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (run-server app {:port 8080}))
