extrX :: Float -> Float -> Float -> Float
extrX a b c = -1*b / 2*a

extrY :: Float -> Float ->Float -> Float
extrY a b c = a*((extrX a b c)^2) +b*(extrX a b c)+ c
