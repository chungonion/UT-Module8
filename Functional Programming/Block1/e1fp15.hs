pyth :: (Ord a,Num a,Enum a) => a -> [(a,a,a)]
pyth n = [ (a,b,c) | a <- [1..n], b <- [1..n], c<-[1..n], a^2 + b^2 == c^2, (a+b+c) <= n]

pyth' :: (Ord a,Num a,Enum a,Integral a) => a -> [(a,a,a)]
pyth' n = [ (a,b,c) | a <- [1..n], b <- [1..n], c<-[1..n], a^2 + b^2 == c^2, a<=b, (a+b+c) <= n, ((gcd a b) == 1)||((gcd b c) == 1)||((gcd a c) == 1)]
