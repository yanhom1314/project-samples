(ns netty4.core
  (:import [io.netty.bootstrap ServerBootstrap]
           [io.netty.channel ChannelHandler SimpleChannelInboundHandler ChannelOption ChannelInitializer]
           [io.netty.channel.nio NioEventLoopGroup]
           [io.netty.channel.socket.nio NioServerSocketChannel]
           [io.netty.handler.codec.string StringDecoder StringEncoder]
           [io.netty.handler.codec DelimiterBasedFrameDecoder Delimiters])
  (:use clojure.stacktrace)
  (:gen-class))

(def host "127.0.0.1")
(def port 9999)

(def echo-server-handler
  (proxy [SimpleChannelInboundHandler] []
    (channelRead0 [ctx msg]
      (println msg)
      (.writeAndFlush ctx (str msg "\r\n")))))

(defn say
  "name"
  [name]
  (prn name)
  (prn name))


(defn call
  [proc]
  (proxy [Runnable] []
    (run []
      (let
        [command (.read System/in)]
        (proc))))
  (proc))

(defn -main [& args]
  (let [b (ServerBootstrap.)
        boss (NioEventLoopGroup.)
        work (NioEventLoopGroup.)
        shutdown #(do
                    (.shutdownGracefully boss)
                    (.shutdownGracefully work))
        decoder (StringDecoder.)
        encoder (StringEncoder.)
        limiter (DelimiterBasedFrameDecoder. 8192 (Delimiters/lineDelimiter))]
    (-> b
      (.group boss work)
      (.channel NioServerSocketChannel)
      (.option ChannelOption/SO_BACKLOG (int 100))
      (.childHandler
        (proxy [ChannelInitializer] []
          (initChannel [ch]
            (-> ch (.pipeline)
              (.addLast
                (into-array ChannelHandler [limiter decoder encoder echo-server-handler]))))))
      (.bind host port))
    (println (str "Server[" host ":" port "] starting..."))))
