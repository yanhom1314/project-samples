module Ext.Tcp(start) where
import Network.Socket
import Control.Concurrent
start :: IO ()
start = do
    sock <- socket AF_INET Stream 0
    bindSocket sock (SockAddrInet 4242 iNADDR_ANY)
    listen sock 10240
    mainLoop sock

mainLoop :: Socket -> IO ()
mainLoop sock = do
    conn <- accept sock
    forkIO $ runConn conn
    mainLoop sock
runConn :: (Socket, SockAddr) -> IO ()
runConn (sock, tcp) = do
    sms<-recv sock 1024
    peeraddr <- getPeerName sock
    putStrLn sms
    runConn (sock, tcp)
