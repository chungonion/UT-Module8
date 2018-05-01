import FPPrac.Trees

data Tree1a = Leaf1a Int| Node1a Int Tree1a Tree1a deriving Show

treeAdd :: Tree1a -> Int -> Tree1a
treeAdd (Leaf1a a) x = (Leaf1a (a+x))
treeAdd (Node1a a treeA treeB) x = (Node1a (a+x)) (treeAdd treeA x) (treeAdd treeB x)

treeSquare :: Tree1a -> Tree1a
treeSquare (Leaf1a a) = (Leaf1a (a*a))
treeSquare (Node1a a treeA treeB) = (Node1a (a*a)) (treeSquare treeA) (treeSquare treeB)
