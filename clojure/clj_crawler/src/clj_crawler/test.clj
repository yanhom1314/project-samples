(defn say-nice [name]
  (println name))

(defn say-hi [name]
  (println name))

(defn say-hello [name]
  (println "Hello " name))

(defn -main [&args]
  (say-nice "Hello World!")
  (say-hi "Hello Nice!")
  (say "Hello World!")
  (say-hello "YaFengLi"))
