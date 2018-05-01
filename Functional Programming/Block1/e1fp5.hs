start :: Float -> Float -> Float -> Float
start a r n = calculateInterest a r (nToInteger n)


calculateInterest :: Float -> Float -> Int -> Float
calculateInterest a r n
    | n <= 0 = a
    | otherwise = calculateInterest a r (n-1) * (1 + r * 0.01)

nToInteger :: Float -> Int
nToInteger n
    |(n>=0) = floor n
    |otherwise = 0
