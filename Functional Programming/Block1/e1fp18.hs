import Data.List
import Test.QuickCheck

iSort :: (Ord a) => [a] -> [a]
iSort [] = []
iSort (first:remains) = before ++ [first] ++ after
    where (before, after) = partition (< first) (iSort remains)

qSort :: (Ord a) => [a] -> [a]
qSort [] = []
qSort (first:remains) = (qSort before) ++ [first] ++ (qSort after)
    where (before, after) = partition (< first) remains

sSort :: (Ord a) => [a] -> [a]
sSort [] = []
sSort list = first:(sSort remains)
    where (first, remains) = fetchSmallest list
          fetchSmallest (first:[]) = (first, [])
          fetchSmallest (first:remains) =
            if first < smallest
                then (first, smallest:others)
                else (smallest, first:others)
            where (smallest, others) = fetchSmallest remains

mSort :: (Ord a) => [a] -> [a]
mSort [] = []
mSort (first:[]) = first:[]
mSort list = merge (mSort front) (mSort back)
    where (front, back) = splitAt ((length list) `div` 2) list
          merge list [] = list
          merge [] list = list
          merge list1 list2 =
              if first1 < first2
                  then first1:(merge (tail list1) list2)
                  else first2:(merge list1 (tail list2))
              where first1 = head list1
                    first2 = head list2


bSort :: (Ord a) => [a] -> [a]
bSort [] = []
bSort (first:[]) = first:[]
bSort (first:remains)
    |first < smallest =  first:(bSort bubbledRemains)
    |otherwise = smallest:(bSort (first:(tail bubbledRemains)))
    where bubbledRemains = bSort remains
          smallest = head bubbledRemains

minMax :: (Ord a) => [a] -> [a]
minMax [] = []
minMax [x] = [x]
minMax x = [minX] ++ (minMax xs) ++ [maxX]
    where
        minX = minimum x
        maxX = maximum x
        xs = delete maxX (delete minX x)
