package pp.block1.cc.dfa;

public class MyChecker implements Checker {
    @Override
    public boolean accepts(State start, String word) {
        State currentState = start;

        for (int i =0 ;i<word.length();i++){
            if (!currentState.hasNext(word.charAt(i))){
                return false;
            }
            currentState = currentState.getNext(word.charAt(i));

        }

        return currentState.isAccepting();
    }
}
