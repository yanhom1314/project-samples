-- 测试内容
module Test where


doubleMe x = x * 2
doubleUs x y = x*2 + y*2
doubleSn x = if x > 10
			then x * 2
			else x
			
fib x
	| x < 2 = x
	| otherwise = fib (x - 1) + fib (x - 2)

main = do
    putStrLn "Greetings!  What is your name?"
    inpStr <- getLine
    putStrLn $ "Welcome to Haskell, " ++ inpStr ++ "!"
