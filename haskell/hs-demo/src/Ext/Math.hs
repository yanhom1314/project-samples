module Ext.Math
    (add,
     add',
     fac,
     double
    ) where

add::(Int,Int) -> Int
add(x,y) = x + y

add' ::Int -> Int -> Int
add' x y = add(x,y)

double :: Int -> Int
double x = add(x,x)

fac :: Integer -> Integer
--fac 0 = 1
--fac n | n > 0 = n * fac(n-1)
fac n = _fac n 1

_fac :: Integer -> Integer -> Integer
_fac 0 temp = temp
_fac n temp = _fac (n-1) (n * temp)
