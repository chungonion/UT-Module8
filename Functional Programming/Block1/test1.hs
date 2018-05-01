fibSeq::[Integer]
fibSeq = 0 : 1 : zipWith (+) fibSeq (tail fibSeq)
