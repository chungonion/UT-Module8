import           Data.List
import           Data.Maybe
import           FPPrac.Trees
import           Test.QuickCheck

data Tree1a = Leaf1a Int| Node1a Int Tree1a Tree1a deriving Show
data Tree1b = Leaf1b (Int,Int)| Node1b (Int,Int) Tree1b Tree1b deriving Show
data Tree1c = Leaf1c Int| Node1c Tree1c Tree1c deriving Show
data Tree1d = Leaf1d (Int,Int)| Node1d Tree1d Tree1d deriving Show
data TreeInt = LeafInt | NodeInt Int TreeInt TreeInt deriving Show

class BinMirror a where
    binMirror :: a -> a

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a a) = RoseNode (show $ Leaf1a a) []
pp1a (Node1a a treeA treeB) = RoseNode  (show $ Leaf1a a) ([pp1a treeA]++[pp1a treeB])

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b a) = RoseNode (show $ Leaf1b a) []
pp1b (Node1b a treeA treeB) = RoseNode  (show $ Leaf1b a) ([pp1b treeA]++[pp1b treeB])

pp1c :: Tree1c -> RoseTree
pp1c (Leaf1c a)           = RoseNode (show $ Leaf1c a) []
pp1c (Node1c treeA treeB) = RoseNode [] ([pp1c treeA]++[pp1c treeB])

pp1d :: Tree1d -> RoseTree
pp1d (Leaf1d a)           = RoseNode (show $ Leaf1d a) []
pp1d (Node1d treeA treeB) = RoseNode [] ([pp1d treeA]++[pp1d treeB])

--ex20

treeAdd :: Tree1a -> Int -> Tree1a
treeAdd (Leaf1a a) x = (Leaf1a (a+x))
treeAdd (Node1a a treeA treeB) x = (Node1a (a+x)) (treeAdd treeA x) (treeAdd treeB x)

treeSquare :: Tree1a -> Tree1a
treeSquare (Leaf1a a) = (Leaf1a (a*a))
treeSquare (Node1a a treeA treeB) = (Node1a (a*a)) (treeSquare treeA) (treeSquare treeB)

mapTree :: (Int -> Int) -> Tree1a -> Tree1a
mapTree func (Leaf1a a) = (Leaf1a (func a))
mapTree func (Node1a a treeA treeB) = (Node1a (func a)) (mapTree func treeA) (mapTree func treeB)

addNode :: Tree1b -> Tree1a
addNode (Leaf1b (a,b)) = Leaf1a (a+b)
addNode (Node1b (a,b) treeA treeB) = (Node1a (a+b)) (addNode treeA) (addNode treeB)

mapTree1b :: ((Int,Int) -> Int) -> Tree1b ->  Tree1a
mapTree1b func (Leaf1b (a,b)) = Leaf1a (func (a,b))
mapTree1b func (Node1b (a,b) treeA treeB) = (Node1a (func (a,b))) (mapTree1b func treeA) (mapTree1b func treeB)

--Ex 21
instance BinMirror Tree1a where
    binMirror x = binMirror1a x

binMirror1a :: Tree1a -> Tree1a
binMirror1a (Leaf1a a)             = (Leaf1a a)
binMirror1a (Node1a a treeA treeB) = Node1a a treeB treeA

instance BinMirror Tree1d where
    binMirror x = binMirror1d x

binMirror1d :: Tree1d -> Tree1d
binMirror1d (Leaf1d a)           = (Leaf1d a)
binMirror1d (Node1d treeA treeB) = Node1d treeB treeA


--Ex 22
-- insertTree TO BE TESTED!!!
insertTree :: Int -> TreeInt -> TreeInt
insertTree input (LeafInt) = NodeInt input LeafInt LeafInt
insertTree input (NodeInt a treeA treeB)
    |input <= a = NodeInt a (insertTree input treeA) treeB
    |input > a = NodeInt a treeA (insertTree input treeB)

--recursion version
makeTree :: [Int] -> TreeInt
makeTree [x]    = insertTree x LeafInt
makeTree (x:xs) = insertTree x $ makeTree xs

--foldr version
makeTreeFoldR :: [Int] -> TreeInt
makeTreeFoldR x = foldr (insertTree) LeafInt x

makeList :: TreeInt -> [Int]
makeList (NodeInt number LeafInt LeafInt) = [number]
makeList (NodeInt number treeA LeafInt) = (makeList treeA) ++ [number]
makeList (NodeInt number LeafInt treeB) = [number]++ (makeList treeB)
makeList (NodeInt number treeA treeB) = (makeList treeA) ++ [number] ++(makeList treeB)

--Ex 23
subTreeAt :: TreeInt -> Int -> Maybe TreeInt
subTreeAt LeafInt _ = Nothing
subTreeAt (NodeInt nodeData treeA treeB) query
    |nodeData == query = Just (NodeInt nodeData LeafInt LeafInt)
    |query <= nodeData = subTreeAt treeA query
    |otherwise = subTreeAt treeB query

--Ex 24
cutoffAt :: TreeInt -> Int -> TreeInt
cutoffAt LeafInt _ = LeafInt
cutoffAt (NodeInt number treeA treeB) level
    | level == 0 = (NodeInt number LeafInt LeafInt)
    | otherwise = (NodeInt number (cutoffAt treeA (level - 1)) (cutoffAt treeB (level - 1)))

--Ex 25
