data MyList a = Nil | Cons a (MyList a) deriving (Show,Eq)

instance Functor MyList where
    fmap f x = myFmap f x


mylst = Cons 1 $ Cons 2 $ Cons 3 $ Nil

myFmap :: (a->b) -> MyList a -> MyList b
myFmap _ Nil = Nil
myFmap f (Cons val restList) = Cons (f val) $ (myFmap f restList)
