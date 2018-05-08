import FPPrac.Trees
import Data.Char

data BinTree a b = Leaf b | Node a (BinTree a b) (BinTree a b) deriving Show
data Value = Const Int | Id String deriving Show

-- Sets of available characters.

--Q2 Part 2

checkToken :: [Char] -> Char -> Bool
checkToken [] desireToken = False
checkToken [x] desireToken = (x == desireToken)
checkToken (x:xs) desireToken = (x == desireToken)


parseExpr :: String -> (BinTree Char Value, String)
parseExpr input
    | checkToken ys '+' = (Node '+' treeA treeB,zs)
    | otherwise = (treeA, ys)
    where
        (treeA, ys) = parseTerm input
        -- (treeA,[]) = parseTerm input
        (treeB, zs) = parseExpr (tail ys)


parseTerm :: String -> (BinTree Char Value, String)
parseTerm input
    | checkToken ys '*' = (Node '*' treeA treeB ,zs)
    | otherwise = (treeA, ys)
     where
         (treeA, ys) = parseFactor input
            -- (treeA,[]) = parseFactor input
         (treeB, zs) = parseTerm (tail ys)


parseFactor :: String -> (BinTree Char Value, String)
parseFactor (x:xs)
    |isDigit x = (Leaf (Const (read [x])), xs++[])
    |isLetter x = (Leaf (Id [x]), xs++[])
    | x == '('   = (treeA, zs)
    -- | (x:xs) == []  = error "parse error in Factor1"
    | otherwise  = error "parse error in Factor"
   where
       (treeA, ys) = parseExpr xs
       (')':zs) = ys
