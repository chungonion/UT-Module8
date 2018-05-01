allEqual :: [Int] -> Bool
allEqual xs
    |tail xs == [] = True
    |otherwise = ((head xs) == (head (tail xs))) && allEqual(tail xs)

isAs :: [Int] -> Bool
isAs xs = allEqual $ generatediffarray xs

generatediffarray :: [Int] -> [Int]
generatediffarray xs
    |tail xs == [] = []
    |otherwise = [((head (tail xs))-(head xs))] ++ generatediffarray(tail xs)
