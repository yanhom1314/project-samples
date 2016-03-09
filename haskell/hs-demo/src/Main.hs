module Main where

import qualified Geometry.Cube   as Cube
import qualified Geometry.Sphere as Sphere
import           Text.Printf

import           Ext.Util
--import qualified Ext.Math as Math
import           Ext.Math
import           Ext.Tcp

main :: IO()

main = do
  putStrLn "Hello World!"
  print (zip [1..10]['a'..'z'])
  putStrLn $ printf "fac 55:%d\n" (fac 55)
  print (add' 5 5)
  print (add' 6 6)
  print (add(7,7))
  print (fac 33)
  print (fac 33)
  let result = add' 11 11
  let r1 = add' 111 111
  let r2 = Cube.area 11.6
  let a1 = Cube.area 12.3
  let a2 = Cube.area 11.3
  let a3 = Sphere.sumArea 11.4
  print result
  print a1
  print a2
  print a3
  print a3
  print (double 12)
  putStrLn(show(fac 66) ++ show(Cube.area 23)) 
  putStrLn(show(double 44) ++ show(fac 66) ++ show(Cube.area 18.7))
  putStrLn("[5..13]:" ++ show(boomBangs [5..13]) ++ "!")
  name <- getLine
  putStrLn("Hello, " ++ reverseWords name ++"!")
  --start
