(ns
  ^{:author wp}
  com.jp.crawler.spider-test
  (:use com.jp.crawler.core)
  (:use com.jp.crawler.tofiles))

(def file-handler (tofile-handler {:directory "/tmp/..." :extension ".txt"}))

(def c (init {
               :url "http://examle/..."
               :handler file-handler
               :workers 2
               :url-limit 20
               :http-opts {}
               :host-limit true
               :polite? true}))
(stop-jobs c)
