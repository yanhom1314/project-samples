(ns
  ^{:author wp}
  com.jp.crawler.core
  (:require [cemerick.url :refer [url]]
            [clojure.string :as s]
            [clojure.tools.logging :refer [debug error info trace warn]]
            [clojure.set :as set]
            [clj-http.client :as http]
            [slingshot.slingshot :refer [get-thrown-object try+]])
  (:import (java.net URL)
           (java.util.concurrent LinkedBlockingQueue TimeUnit)))

(defn valid-url?
  "测试是否url是符合规则"
  [url-str]
  (try
    (url url-str)
    (catch Exception _ nil)))

(defn- enqueue*
  "入队列操作，形式{:url and :count}"
  [config url]
  (trace :enqueue-url url)
  (.put (-> config :state :url-queue)
    {:url url :count @(-> config :state :url-count)})
  (swap! (-> config :state :url-count) inc))

(defn enqueue-url
  "入队列前的判断，不超过url清单总数限制以及之前没有被抓取过"
  [config url]
  (if (get @(-> config :state :seen-urls) url)
    (swap! (-> config :state :seen-urls) update-in [url] inc)
    (when (or (neg? (:url-limit config))
            (< @(-> config :state :url-count) (:url-limit config)))
      (when-let [url-info (valid-url? url)]
        (swap! (-> config :state :seen-urls) assoc url 1)
        (if-let [host-limiter (:host-limit config)]
          (when (.contains (:host url-info) host-limiter)
            (enqueue* config url))
          (enqueue* config url))))))

(defn- crawl-page
  "抓取网页的函数"
  [config url-map]
  (try+
    (trace :retrieving-body-for url-map)
    (let [url (:url url-map)
          score (:count url-map)
          body (:body (http/get url (:http-opts config)))
          ]
      (try
        ((:handler config) (assoc url-map :body body))
        (catch Exception e
          (error e "Exception executing handler"))))
    (catch java.net.SocketTimeoutException e
      (trace "connection timed out to" (:url url-map)))
    (catch org.apache.http.conn.ConnectTimeoutException e
      (trace "connection timed out to" (:url url-map)))
    (catch java.net.UnknownHostException e
      (trace "unknown host" (:url url-map) "skipping."))
    (catch org.apache.http.conn.HttpHostConnectException e
      (trace "unable to connect to" (:url url-map) "skipping"))
    (catch map? m
      (debug "unknown exception retrieving" (:url url-map) "skipping.")
      (debug (dissoc m :body) "caught"))
    (catch Object e
      (debug e "!!!"))))

(defn- job-fn
  "实施抓取操作的工作线程"
  [config]
  (fn worker-fn* []
    (loop []
      (trace "从queue获取url"
        (.size (-> config :state :url-queue)) "items")
      (when-let [url-map (.poll (-> config :state :url-queue)
                           3 TimeUnit/SECONDS)]
        (trace :got url-map)
        (crawl-page config url-map))
      (let [tid (.getId (Thread/currentThread))]
        (trace :running? (get @(-> config :state :worker-urls) tid))
        (let [state (:state config)
              limit-reached (and (pos? (:url-limit config))
                              (= @(:url-count state) (:url-limit config))
                              (zero? (.size (:url-queue state))))]
          (when-not (get @(:worker-urls state) tid)
            (debug "terminating my url"))
          (when limit-reached
            (debug (str "url limit reached: (" @(:url-count state)
                     "/" (:url-limit config) "), terminating myself")))
          (when (and (get @(:worker-urls state) tid)
                  (not limit-reached))
            (recur)))))))

(defn start-job
  "启动抓取操作，会创建一个线程"
  [config]
  (let [w-thread (Thread. (job-fn config))
        _ (.setName w-thread (str "crawler-worker-" (.getName w-thread)))
        w-tid (.getId w-thread)]
    (dosync
      (alter (-> config :state :worker-urls) assoc w-tid true)
      (alter (-> config :state :running-workers) conj w-thread))
    (info "Starting thread:" w-thread w-tid)
    (.start w-thread))
  (info "New worker count:" (count @(-> config :state :running-workers))))

(def default-http-opts {:socket-timeout 10000
                        :conn-timeout 10000
                        :insecure? true
                        :throw-entire-message? false})

(defn init
  "启动函数"
  [options]
  (trace :options options)
  (let [hl (:host-limit options)
        host-limiter (cond
                       (string? hl) (try (:host (url hl))
                                      (catch Exception _ hl))
                       (= true hl) (:host (url (:url options)))
                       :else hl)
        _ (trace :host-limiter host-limiter)
        config (merge {:workers 5
                       :url-limit 100
                       :state {:url-queue (LinkedBlockingQueue.)
                               :url-count (atom 0)
                               :running-workers (ref [])
                               :worker-urls (ref {})
                               :seen-urls (atom {})
                               }
                       :http-opts default-http-opts}
                 options
                 {:host-limit host-limiter})]
    (trace :config config)
    (info "Starting" (:workers config) "workers...")
    (http/with-connection-pool {:timeout 5
                                :threads (:workers config)
                                :insecure? true}
      (dotimes [_ (:workers config)]
        (start-job config))
      (info "Starting crawl of" (:url config))
      (enqueue-url config (:url config)))
    config))

(defn thread-status
  "获取当前线程内部的信息"
  [config]
  (zipmap (map (memfn getId) @(-> config :state :running-workers))
    (map (memfn getState) @(-> config :state :running-workers))))

(def terminated Thread$State/TERMINATED)

(defn stop-jobs
  "定制当前的工作线程"
  [config]
  (when (pos? (count @(-> config :state :running-workers)))
    (info "Strangling urls...")
    (dosync
      (ref-set (-> config :state :worker-urls) {})
      (info "Waiting for workers to finish...")
      (map #(.join % 30000) @(-> config :state :running-workers))
      (Thread/sleep 10000)
      (if (= #{terminated} (set (vals (thread-status config))))
        (do
          (info "All workers stopped.")
          (ref-set (-> config :state :running-workers) []))
        (do
          (warn "Unable to stop all workers.")
          (ref-set (-> config :state :running-workers)
            (remove #(= terminated (.getState %))
              @(-> config :state :running-workers)))))))
  @(-> config :state :running-workers))
