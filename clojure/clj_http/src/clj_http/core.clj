(ns clj-http.core
  (:gen-class)
  (:use clj-http.lyf)
  (:use org.httpkit.server))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello  HTTP!</h1>"})

;;不知道怎么搞啊，闹心啊
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (say-nice "yafengli.dream@gmail.com")
  (say-hello "ya_feng_li@aliyum.com")
  (say-hi "ya_feng_li@163.com")
  (say-hello (lyf-add 445 333))
  (say-hello (lyf-del 444 222))  
  (lib-nice "yafengli@sina.com")
  (println "Listern to 127.0.0.1:9002, Server starting.")
  (run-server app {:port 9002}))
