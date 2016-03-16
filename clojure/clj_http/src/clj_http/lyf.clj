(ns clj-http.lyf)
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
(defn lyf-del
  "del x - y"
  [x y]
  (- x y))
(defn say-nice
  "This function is say Nice to your friends!"
  [name]
  (println "Nice, " name))
