import           Test.QuickCheck

prop_mytake n xs = n >= 0 ==> mytake n xs == take n xs
-- prop_mymaximum xs = not (null xs) ==> mymaximum xs == maximum xs
prop_myreverse xs = not (null xs) ==>myreverse xs == reverse xs
-- for QuickCheck checking

mylength :: [Int] -> Int
mylength list
    |tail list == [] = 1
    |otherwise = mylength (tail list) + 1

mysum :: [Int] -> Int
mysum list
    |tail list == [] = head list
    |otherwise = mysum (tail list) + (head list)

myreverse :: [Int] -> [Int]
myreverse list
    |tail list == [] = [head list]
    |otherwise = myreverse (tail list) ++ [(head list)]

mytake :: Int -> [Int] -> [Int]
mytake n list
    |list == [] = []
    |n==0 = []
    |tail list == [] = [head list]
    |otherwise = [head list] ++ mytake (n-1) (tail list)

myelem :: [Int] -> Int -> Bool
myelem list input
    |list == [] = False
    |head list == input = True
    |otherwise = myelem (tail list) input

myconcat :: [Int] -> [Int]-> [Int]
myconcat listA listB
    |listA == [] = listB
    |otherwise = [head listA] ++ myconcat (tail listA) listB

-- mymaximum :: [Int] -> Int
-- mymaximum list
--     |tail list == [] = head list
--     |otherwise = head

myzip :: [Int] -> [Int] -> [(Int,Int)]
myzip listA listB
    |(tail listA == []) || (tail listB == []) = [(head listA, head listB)]
    |otherwise = [(head listA, head listB)]++myzip (tail listA) (tail listB)
