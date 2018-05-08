/**
 * 
 */
package pp.block2.cc.ll;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.SymbolFactory;
import pp.block2.cc.Term;

/**
 * Class containing some example grammars.
 * @author Arend Rensink
 *
 */
public class Grammars {
	/** Returns a grammar for simple English sentences. */
	public static Grammar makeSentence() {
		// Define the non-terminals
		NonTerm sent = new NonTerm("Sentence");
		NonTerm subj = new NonTerm("Subject");
		NonTerm obj = new NonTerm("Object");
		NonTerm mod = new NonTerm("Modifier");
		// Define the terminals, using the Sentence.g4 lexer grammar
		// Make sure you take the token constantss from the right class!
		SymbolFactory fact = new SymbolFactory(Sentence.class);
		Term noun = fact.getTerminal(Sentence.NOUN);
		Term verb = fact.getTerminal(Sentence.VERB);
		Term adj = fact.getTerminal(Sentence.ADJECTIVE);
		Term end = fact.getTerminal(Sentence.ENDMARK);
		// Build the context free grammar
		Grammar g = new Grammar(sent);
		g.addRule(sent, subj, verb, obj, end); //Class: NonTerm -> NonTerm, Term, NonTerm Term
		g.addRule(subj, noun);
		g.addRule(subj, mod, subj);
		g.addRule(obj, noun);
		g.addRule(obj, mod, obj);
		g.addRule(mod, adj);
		//Basically meands mod -> adj
		return g;
	}


	public static Grammar makeIf(){
	    NonTerm elsePart = new NonTerm("ElsePart");
	    NonTerm stat = new NonTerm("Stat");
        SymbolFactory fact = new SymbolFactory(If.class);
        Term ifTerm = fact.getTerminal(If.IF);
        Term thenTerm = fact.getTerminal(If.THEN);
        Term condTerm = fact.getTerminal(If.COND);
        Term elseTerm = fact.getTerminal(If.ELSE);
        Term assignTerm = fact.getTerminal(If.ASSIGN);


        Grammar g = new Grammar(stat);
        g.addRule(stat,assignTerm);
        g.addRule(stat,ifTerm,condTerm,thenTerm,stat,elsePart);
        g.addRule(elsePart,elseTerm,stat);
        g.addRule(elsePart,Symbol.EMPTY);


        return g;
    }

    public static Grammar makeCC2(){
	    NonTerm cNonTerm = new NonTerm("C");
	    NonTerm lNonTerm = new NonTerm("L");
	    NonTerm rNonTerm = new NonTerm("R");
	    NonTerm rPrimeNonTerm = new NonTerm("R'");
	    NonTerm qNonTerm = new NonTerm("Q");
	    SymbolFactory fact = new SymbolFactory(CC2.class);
	    Term aTerm = fact.getTerminal(CC2.A);
	    Term bTerm = fact.getTerminal(CC2.B);
	    Term cTerm = fact.getTerminal(CC2.C);


	    Grammar g = new Grammar(lNonTerm);
	    g.addRule(cNonTerm,cTerm);
	    g.addRule(cNonTerm,Symbol.EMPTY);
	    g.addRule(lNonTerm,rNonTerm,aTerm);
	    g.addRule(lNonTerm,bTerm,qNonTerm,bTerm,aTerm);
	    g.addRule(rNonTerm,cNonTerm,aTerm,bTerm,aTerm,rPrimeNonTerm);
        g.addRule(rPrimeNonTerm,bTerm,cTerm,rPrimeNonTerm);
	    g.addRule(rPrimeNonTerm,Symbol.EMPTY);
	    g.addRule(qNonTerm,bTerm,cTerm);
	    g.addRule(qNonTerm,cTerm);


	    return g;

    }

//	public static void main(String[] args) {
//		MyLLCalc2 myLLCalc = new MyLLCalc2(makeSentence());
//		myLLCalc.getFirst();
//
//	}
}
