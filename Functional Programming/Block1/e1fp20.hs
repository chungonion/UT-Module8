import FPPrac.Trees

data Tree1a = Leaf1a Int| Node1a Int Tree1a Tree1a deriving Show
data Tree1b = Leaf1b (Int,Int)| Node1b (Int,Int) Tree1b Tree1b deriving Show

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

-- mapTree1b :: ((Int,Int) -> Int) -> Tree1b ->  Tree1a
-- mapTree1b func (Leaf1b (a,b)) = Leaf1a 
