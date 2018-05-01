package pp.block1.cc.dfa;

import java.util.ArrayList;
import java.util.List;

public class MyScanner implements Scanner {
    @Override
    public List<String> scan(State dfa, String text) {
        State currentState = dfa;
        ArrayList<String> tokenList = new ArrayList<>();
        int tokenStart = 0;
        int tokenLastAccept = 0;
        boolean hasToken = false;
        int i= 0;
        int j= 0;

        while (tokenStart <text.length()){
            if (i >= text.length()){
                if (hasToken){
                    tokenList.add(text.substring(tokenStart,tokenLastAccept+1));
                    i = tokenLastAccept;
                    tokenStart = i+1;
                    hasToken = false;
                    currentState = dfa;
                }else{
                    break;
                }
            }
            if (currentState.hasNext(text.charAt(i))) {
                currentState = currentState.getNext(text.charAt(i));

                if (currentState.isAccepting()) {
                    tokenLastAccept = i;
                    hasToken = true;
                }

            } else {
                if (hasToken){
                    tokenList.add(text.substring(tokenStart,tokenLastAccept+1));
                    i = tokenLastAccept;
                    tokenStart = i+1;
                    hasToken = false;
                    currentState = dfa;
                }
            }
            i++;
        }

        if (currentState.isAccepting()) {
            tokenLastAccept = i;
        }

        if (hasToken){
            tokenList.add(text.substring(tokenStart,tokenLastAccept+1));
        }

        return tokenList;
    }
}
