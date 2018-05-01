import Data.Char
import Test.QuickCheck

check xs = code xs 3 == map (chr.(+3).ord) xs

code :: [Char] -> Int -> [Char]
code x n = map (`codeLetter` n) x

codeLetter :: Char->Int -> Char
codeLetter x n
    | (ord(x)>=65) && (ord(x)<=90) = chr(changeUpperCode(ord(x)+n))
    | (ord(x)>=97) && (ord(x)<=122) = chr(changeLowerCode(ord(x)+n))
    | otherwise = x


changeUpperCode :: Int -> Int
changeUpperCode y
    | y > 90 = y - 26
    | otherwise = y

changeLowerCode :: Int -> Int
changeLowerCode y
    | y > 122 = y - 26
    | otherwise = y

checkN :: Int -> Int
checkN x
    | (x>=0) && (x<=26) = x
    | otherwise = 0
