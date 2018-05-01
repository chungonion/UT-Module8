r :: Num a => a -> a -> [a]
r a d = [a] ++ r (a+d) d

r1:: Num a => a -> a -> Int -> a
r1 a d n =  (r a d) !! (n)

totalr :: Int -> Int -> [Int] -> Int
totalr i j list = do
    sum (drop i (take (j+1) list))
