checkPrime :: Int -> Bool
checkPrime i = elem i (takeWhile (<= i) sieve)

firstNPrime :: Int -> [Int]
firstNPrime i = take i sieve

primeSmallerThanN :: Int -> [Int]
primeSmallerThanN i = takeWhile (< i) sieve

sieve :: [Int]
sieve = list ([2..])
  where
    list (p:xs) = p : list [x|x <- xs, x `mod` p > 0]
