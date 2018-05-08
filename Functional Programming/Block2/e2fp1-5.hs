import           FPPrac.Trees
import Data.Char
-- Q1
data BinTree a b = Leaf b | Node a (BinTree a b) (BinTree a b) deriving Show
-- Q3 Declarations
data Token = Numbers
            | Identifiers
            | LBracket
            | RBracket
            | Addition
            | Multiplication
            | Id String deriving Show

letter = ['a'..'z'] ++ ['A'..'Z']
digit  = ['0' .. '9']
letdig = letter ++ digit

ppBin :: (Show a, Show b)=> (BinTree a b,String) -> RoseTree
ppBin ((Leaf x),_)               = RoseNode (show x) []
ppBin ((Node x child1 child2),_) = RoseNode (show x) [(ppBin (child1,"")), (ppBin (child2,""))]


--Q2

checkToken :: [Char] -> Char -> Bool
checkToken [] desireToken = False
checkToken [x] desireToken = (x == desireToken)
checkToken (x:xs) desireToken = (x == desireToken)


parseExpr :: String -> (BinTree Char Int, String)
parseExpr input
    | checkToken ys '+' = (Node '+' treeA treeB,zs)
    | otherwise = (treeA, ys)
    where
        (treeA, ys) = parseTerm input
        (treeB, zs) = parseExpr (tail ys)

parseTerm :: String -> (BinTree Char Int, String)
parseTerm input
    | checkToken ys '*' = (Node '*' treeA treeB ,zs)
    | otherwise = (treeA, ys)
     where
         (treeA, ys) = parseFactor input
         (treeB, zs) = parseTerm (tail ys)

parseFactor :: String -> (BinTree Char Int, String)
parseFactor (x:xs) = (Leaf (read [x]), xs)

--Q3
tokenizer :: String -> [Token]
tokenizer []         = []
tokenizer (' ':xs)   = tokenizer xs
tokenizer ('\t':xs)  = tokenizer xs
tokenizer ('(' : xs) = LBracket : tokenizer xs
tokenizer (')' : xs) = RBracket : tokenizer xs
tokenizer ('+' : xs) = Addition : tokenizer xs
tokenizer ('*' : xs) = Multiplication : tokenizer xs
tokenizer s@(x:xs)
    | x `elem` letter = Id id : tokenizer rem
  where (id, rem) = span (`elem` letdig) s

-- --Q4
-- parseExpr' :: [Token] -> (BinTree Char Int)
-- parseExpr' tokens =
