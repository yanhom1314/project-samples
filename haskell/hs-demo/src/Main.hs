module Main where

import Text.Printf
import qualified Geometry.Sphere as Sphere
import qualified Geometry.Cube as Cube

main :: IO()

main = do
  putStrLn "Hello World!"
  print (zip [1..10]['a'..'z'])
  putStrLn $ printf "The value is %d" (fac(51))
  print (add' 5 5)
  print (add'(6)(6))
  print (add(7,7))
  let result = add' 11 11
  let r1 = add' 111 111
  let a1 = Cube.area(12.3)
  print result
  print a1

add::(Int,Int) -> Int
add(x,y) = x + y

add' ::Int ->(Int -> Int)
add' x y = x + y

fac :: Integer -> Integer
fac 0 = 1
fac n | n > 0 = n * fac(n-1)
