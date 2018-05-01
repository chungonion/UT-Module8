import Test.QuickCheck

checkCommutivityAdd x y = (x>=0) && (y>=0) ==> x+y == y+x
checkCommutivitySubtract x y= (x>=0) && (y>=0) ==> y-x == x-y
