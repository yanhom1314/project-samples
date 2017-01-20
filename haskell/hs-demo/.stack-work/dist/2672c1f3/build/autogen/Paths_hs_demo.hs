module Paths_hs_demo (
    version,
    getBinDir, getLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
catchIO = Exception.catch

version :: Version
version = Version [1,0] []
bindir, libdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "E:\\Github\\project-samples\\haskell\\hs-demo\\.stack-work\\install\\e4ebb170\\bin"
libdir     = "E:\\Github\\project-samples\\haskell\\hs-demo\\.stack-work\\install\\e4ebb170\\lib\\x86_64-windows-ghc-7.10.3\\hs-demo-1.0-2NWztJtaNH9KVKSxOE3bI7"
datadir    = "E:\\Github\\project-samples\\haskell\\hs-demo\\.stack-work\\install\\e4ebb170\\share\\x86_64-windows-ghc-7.10.3\\hs-demo-1.0"
libexecdir = "E:\\Github\\project-samples\\haskell\\hs-demo\\.stack-work\\install\\e4ebb170\\libexec"
sysconfdir = "E:\\Github\\project-samples\\haskell\\hs-demo\\.stack-work\\install\\e4ebb170\\etc"

getBinDir, getLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "hs_demo_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "hs_demo_libdir") (\_ -> return libdir)
getDataDir = catchIO (getEnv "hs_demo_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "hs_demo_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "hs_demo_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "\\" ++ name)
