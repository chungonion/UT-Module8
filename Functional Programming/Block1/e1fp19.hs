import FPPrac.Trees

data Tree1a = Leaf1a Int| Node1a Int Tree1a Tree1a deriving Show
data Tree1b = Leaf1b (Int,Int)| Node1b (Int,Int) Tree1b Tree1b deriving Show

pp1a :: Tree1a -> RoseTree
pp1a (Leaf1a a) = RoseNode (show $ Leaf1a a) []
pp1a (Node1a a treeA treeB) = RoseNode  (show $ Leaf1a a) ([pp1a treeA]++[pp1a treeB])

pp1b :: Tree1b -> RoseTree
pp1b (Leaf1b a) = RoseNode (show $ Leaf1b a) []
pp1b (Node1b a treeA treeB) = RoseNode  (show $ Leaf1b a) ([pp1b treeA]++[pp1b treeB])
