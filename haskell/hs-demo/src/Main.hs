module Main where

import Text.Printf
import qualified Geometry.Sphere as Sphere
import qualified Geometry.Cube as Cube

import Ext.Util
--import qualified Ext.Math as Math
import Ext.Math
import Ext.Tcp

main :: IO()

main = do
  putStrLn "Hello World!"
  print (zip [1..10]['a'..'z'])
  putStrLn $ printf "fac 55:%d\n" (fac 55)
  print (add' 5 5)
  print (add' 6 6)
  print (add(7,7))
  let result = add' 11 11
  let r1 = add' 111 111
  let a1 = Cube.area 12.3
  print result
  print a1
  putStrLn("[5..13]:" ++ show(boomBangs [5..13]) ++ "!")

  name <- getLine
  putStrLn("Hello, " ++ reverseWords name ++"!")
  --start
