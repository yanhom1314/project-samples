-- 测试内容
module Test where

fib x
	| x < 2 = x
	| otherwise = fib (x - 1) + fib (x - 2)

main = do 
    putStrLn "Greetings!  What is your name?"
    inpStr <- getLine
    putStrLn $ "Welcome to Haskell, " ++ inpStr ++ "!"    
