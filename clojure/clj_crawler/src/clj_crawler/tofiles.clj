(ns
  ^{:author wp}
  com.jp.crawler.tofiles
  "抓取内容本地文档化"
  (:require [clojure.java.io :refer [file]]
            [clojure.tools.logging :refer [info debug trace warn]]
            [clj-http.util :as util]
            ))

(defn tofile-handler
  "处理文档化的操作函数"
  [config]
  (let [directory (file (or (:directory config) "data"))
        extension (or (:extension config) "")
        file-for (fn file-for* [url]
                   (let [url (.replace url "http://" "")
                         url (.replace url "https://" "")]
                     (file directory (str (util/url-encode url) extension))))]
    (when-not (.exists directory)
      (info "Creating directory" (str (.getAbsolutePath directory)))
      (.mkdir directory))
    (fn textfile-handler* [{:keys [url body]}]
      (let [f (file-for url)]
        (trace "writing" (.getAbsolutePath f))
        (spit f (str "URL: " url "\n\n" body))))))
