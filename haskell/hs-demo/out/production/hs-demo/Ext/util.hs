module Ext.Util (reverseWords) where

reverseWords :: String -> String
reverseWords = unwords . map reverse . words