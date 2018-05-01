myfilter :: (a -> Bool) -> [a] -> [a]
myfilter _ [] = []
myfilter func (headA:restA)
    |func headA == True = [headA] ++ myfilter func restA
    |otherwise = (myfilter func restA)

myfoldr :: (b -> a -> b) -> b -> [a] -> b
myfoldr _ x [] = x
myfoldr func x (headA:restA) =
    func (myfoldr func x restA) (headA)

myfoldl :: (b -> a -> b) -> b -> [a] -> b
myfoldl _ x [] = x
myfoldl func x listA =
    func (myfoldl func x (init listA)) (last listA)

myzipWith :: (a->b->c) -> [a] -> [b] ->[c]
myzipWith _ [] _ = []
myzipWith _ _ [] = []
myzipWith func (headA:restA) (headB:restB) =
    func (headA) (headB) : myzipWith func restA restB
