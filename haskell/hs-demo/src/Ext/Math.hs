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
--fac 0 = 1
--fac n | n > 0 = n * fac(n-1)
fac n = fac_ n 1

fac_ :: Integer -> Integer -> Integer
fac_ 0 temp = temp
fac_ n temp = fac_ (n-1) (n * temp)
