increasing :: (Ord a) => [a] -> Bool
increasing (x:x2:xs) = x < x2 && increasing (x2:xs)
increasing [x] = True

weaklyincreasing :: (Ord a, Num a, Fractional a) => [a] -> Bool
weaklyincreasing [x] = True
weaklyincreasing x = last x > ((foldl (+) 0 (init x)) / (fromIntegral (length (init x)))) && weaklyincreasing (init x)


-- testing (x:x2:xs) = (fromIntegral (length xs))
