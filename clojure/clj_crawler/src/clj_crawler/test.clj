(defn say-nice [name]
  (println name))

(defn say-hi [name]
  (println name))

(defn say-hello [name]
  (println "Hello " name))
(defn welcome [name]
  "What the fucking hell!"
  (println name))
(defn say-fuck [name]
  (print name)
  (say-hi name))
(defn -main [&args]
  (say-nice "Hello World!")
  (say-hi "Hello Nice!")
  (say "Hello World!")
  (welcome "YaFengLi")
  (say-hello "YaFengLi"))
