root1 :: Float -> Float-> Float-> Float
root1 a b c
    |(b^2 - 4*a*c <0) = error "negative discriminant"
    |otherwise = -1 * b + sqrt (b^2- 4*a*c)

root2 :: Float -> Float-> Float-> Float
root2 a b c
    |(b^2 - 4*a*c <0) = error "negative discriminant"
    |otherwise = -1 * b - sqrt (b^2- 4*a*c)
