module Ext.Math
    (add,
     add',
     fac
    ) where

add::(Int,Int) -> Int
add(x,y) = x + y

add' ::Int -> Int -> Int
add' x y = add(x,y)

fac :: Integer -> Integer
fac 0 = 1
fac n | n > 0 = n * fac(n-1)
