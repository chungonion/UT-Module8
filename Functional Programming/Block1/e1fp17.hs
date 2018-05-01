sublist :: (Eq a) => [a]->[a]->Bool
sublist [] _  = True
sublist _ [] = False
sublist (x:xs) (y:ys)
    |(x:xs) == take (length (x:xs)) (y:ys) = True
    |otherwise = sublist (x:xs) ys


partialsublist :: (Eq a) => [a]->[a]->Bool
partialsublist [] _  = True
partialsublist _ [] = False
partialsublist (x:xs) (y:ys)
    |x==y = partialsublist xs ys
    |otherwise = partialsublist (x:xs) ys
