allRowsEquallyLong :: [[Int]]->Bool
allRowsEquallyLong (matrixHead:matrixTailHead:matrixTail)
    |matrixTail == [] = True
    |otherwise = (length(matrixHead) == length (matrixTailHead) && allRowsEquallyLong (matrixTailHead:matrixTail))

-- Attempt to use map function

rowTotals :: [[Int]]->[Int]
rowTotals (matrixHead:matrixTail)
    |matrixTail == [] = [(sum(matrixHead))]
    |otherwise = (sum(matrixHead)) : rowTotals(matrixTail)

mytranspose :: [[Int]]->[[Int]]
mytranspose([]:_)  = []
mytranspose matrix = (map head matrix) : mytranspose (map tail matrix)

colTotals :: [[Int]]->[Int]
colTotals matrix
    -- |allRowsEquallyLong matrix == False = []
    -- |otherwise
    = rowTotals (mytranspose matrix)
colTotals matrix = rowTotals . mytranspose matrix
-- f xs = map sum xs
-- f = map sum
