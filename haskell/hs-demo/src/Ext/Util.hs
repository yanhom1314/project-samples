module Ext.Util (reverseWords,boomBangs) where

reverseWords :: String -> String
reverseWords = unwords . map reverse . words

boomBangs:: [Integer] -> [String]
boomBangs xs = [if x < 10 then "BOOM!" else "BANG!" | x <- xs, odd x]
