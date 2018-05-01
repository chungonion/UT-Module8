import FPPrac.Trees

data Tree1a = Leaf1a Int
    | Node1a Int Tree1a Tree1a
    deriving Show

data Tree1b = ()

pp1b :: Tree1b -> RoseTree
pp1b tree =
