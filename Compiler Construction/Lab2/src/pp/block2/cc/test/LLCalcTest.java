package pp.block2.cc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;
import pp.block2.cc.ll.*;

public class LLCalcTest {
    Grammar sentenceG = Grammars.makeSentence();
    // Define the non-terminals
    NonTerm subj = sentenceG.getNonterminal("Subject");
    NonTerm obj = sentenceG.getNonterminal("Object");
    NonTerm sent = sentenceG.getNonterminal("Sentence");
    NonTerm mod = sentenceG.getNonterminal("Modifier");
    // Define the terminals
    Term adj = sentenceG.getTerminal(Sentence.ADJECTIVE);
    Term noun = sentenceG.getTerminal(Sentence.NOUN);
    Term verb = sentenceG.getTerminal(Sentence.VERB);
    Term end = sentenceG.getTerminal(Sentence.ENDMARK);
    // Now add the last rule, causing the grammar to fail
    Grammar sentenceXG = Grammars.makeSentence();

    {
        sentenceXG.addRule(mod, mod, mod);
    }

    LLCalc sentenceXLL = createCalc(sentenceXG);

    /**
     * Tests the LL-calculator for the Sentence grammar.
     */
    @Test
    public void testSentenceOrigLL1() {
        // Without the last (recursive) rule, the grammar is LL-1
        assertTrue(createCalc(sentenceG).isLL1());
    }

    @Test
    public void testSentenceXFirst() {
        Map<Symbol, Set<Term>> first = sentenceXLL.getFirst();
        assertEquals(set(adj, noun), first.get(sent));
        assertEquals(set(adj, noun), first.get(subj));
        assertEquals(set(adj, noun), first.get(obj));
        assertEquals(set(adj), first.get(mod));
    }

    @Test
    public void testSentenceXFollow() {
        // FOLLOW sets
        Map<NonTerm, Set<Term>> follow = sentenceXLL.getFollow();
        assertEquals(set(Symbol.EOF), follow.get(sent));
        assertEquals(set(verb), follow.get(subj));
        assertEquals(set(end), follow.get(obj));
        assertEquals(set(noun, adj), follow.get(mod));
    }

    @Test
    public void testSentenceXFirstPlus() {
        // Test per rule
        Map<Rule, Set<Term>> firstp = sentenceXLL.getFirstp();
        List<Rule> subjRules = sentenceXG.getRules(subj);
        assertEquals(set(noun), firstp.get(subjRules.get(0)));
        assertEquals(set(adj), firstp.get(subjRules.get(1)));
    }

    @Test
    public void testSentenceXLL1() {
        assertFalse(sentenceXLL.isLL1());
    }

    /**
     * Creates an LL1-calculator for a given grammar.
     */
    private LLCalc createCalc(Grammar g) {
        return new MyLLCalc(g); // TODO your implementation of LLCalc (Ex. 2-CC.3)
    }

    @SuppressWarnings("unchecked")
    private <T> Set<T> set(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }


    //	To be completed
//
    Grammar ifG = Grammars.makeIf(); // to be defined (Ex. 2-CC.4.1) // Define the non-terminals
    NonTerm stat = ifG.getNonterminal("Stat");
    NonTerm elsePart = ifG.getNonterminal("ElsePart");
    // Define the terminals (take from the right lexer grammar!)
    Term ifT = ifG.getTerminal(If.IF);
    Term thenT = ifG.getTerminal(If.THEN);
    Term condT = ifG.getTerminal(If.COND);
    Term elseT = ifG.getTerminal(If.ELSE);
    Term assignT = ifG.getTerminal(If.ASSIGN);

    // (other terminals you need in the tests)
    Term eof = Symbol.EOF;
    Term empty = Symbol.EMPTY;
    LLCalc ifLL = createCalc(ifG);


    @Test
    public void testIfFirst() {
        Map<Symbol, Set<Term>> first = ifLL.getFirst();
        assertEquals(set(assignT, ifT), first.get(stat));
        assertEquals(set(elseT, Symbol.EMPTY), first.get(elsePart)); // (insert other tests)
// (insert other tests)
    }

    @Test
    public void testIfFollow() {
        Map<NonTerm, Set<Term>> follow = ifLL.getFollow();
        assertEquals(set(elseT, Symbol.EOF), follow.get(stat)); // (insert other tests)
        assertEquals(set(elseT, Symbol.EOF), follow.get(elsePart)); // (insert other tests)
    }

    @Test
    public void testIfFirstPlus() {
        Map<Rule, Set<Term>> firstp = ifLL.getFirstp();
        List<Rule> elseRules = ifG.getRules(elsePart);
        List<Rule> statRules = ifG.getRules(stat);
        assertEquals(set(elseT), firstp.get(elseRules.get(0))); // (insert other tests)
        assertEquals(set(elseT, Symbol.EMPTY, Symbol.EOF), firstp.get(elseRules.get(1))); // (insert other tests)
        assertEquals(set(assignT), firstp.get(statRules.get(0)));
        assertEquals(set(ifT), firstp.get(statRules.get(1)));
    }

    @Test
    public void testIfLL1() {
        assertFalse(ifLL.isLL1());
    }

    Grammar cc2G = Grammars.makeCC2();
    NonTerm cNonTerm = cc2G.getNonterminal("C");
    NonTerm lNonTerm = cc2G.getNonterminal("L");
    NonTerm rNonTerm = cc2G.getNonterminal("R");
    NonTerm rPrimeNonTerm = cc2G.getNonterminal("R'");
    NonTerm qNonTerm = cc2G.getNonterminal("Q");

    Term aTerm = cc2G.getTerminal(CC2.A);
    Term bTerm = cc2G.getTerminal(CC2.B);
    Term cTerm = cc2G.getTerminal(CC2.C);

    LLCalc cc2LL = createCalc(cc2G);

    @Test
    public void testCC2First() {
        Map<Symbol, Set<Term>> first = cc2LL.getFirst();
        assertEquals(set(cTerm,Symbol.EMPTY), first.get(cNonTerm));
        assertEquals(set(aTerm,bTerm,cTerm), first.get(lNonTerm));
        assertEquals(set(aTerm,cTerm), first.get(rNonTerm));
        assertEquals(set(bTerm,Symbol.EMPTY), first.get(rPrimeNonTerm));
        assertEquals(set(bTerm,cTerm), first.get(qNonTerm));
    }

    @Test
    public void testCC2Follow() {
        Map<NonTerm, Set<Term>> follow = cc2LL.getFollow();
        assertEquals(set(aTerm), follow.get(cNonTerm));
        assertEquals(set(Symbol.EOF), follow.get(lNonTerm));
        assertEquals(set(aTerm), follow.get(rNonTerm));
        assertEquals(set(aTerm), follow.get(rPrimeNonTerm));
        assertEquals(set(bTerm), follow.get(qNonTerm));
    }

    @Test
    public void testCC2FirstPlus() {
        Map<Rule, Set<Term>> firstp= cc2LL.getFirstp();
        List<Rule> cRules = cc2G.getRules(cNonTerm);
        List<Rule> lRules = cc2G.getRules(lNonTerm);
        List<Rule> rRules = cc2G.getRules(rNonTerm);
        List<Rule> rPrimeRules = cc2G.getRules(rPrimeNonTerm);
        List<Rule> qRules = cc2G.getRules(qNonTerm);

        assertEquals(set(aTerm,Symbol.EMPTY), firstp.get(rPrimeRules.get(1)));
        assertEquals(set(bTerm), firstp.get(rPrimeRules.get(0)));
        assertEquals(set(cTerm), firstp.get(cRules.get(0)));
        assertEquals(set(aTerm,Symbol.EMPTY), firstp.get(cRules.get(1)));
        assertEquals(set(aTerm,cTerm), firstp.get(lRules.get(0)));
        assertEquals(set(bTerm), firstp.get(lRules.get(1)));
        assertEquals(set(aTerm,cTerm,Symbol.EMPTY), firstp.get(rRules.get(0)));
        assertEquals(set(bTerm), firstp.get(qRules.get(0)));
        assertEquals(set(cTerm), firstp.get(qRules.get(1)));

    }

    @Test
    public void testcc2LL1() {
        assertTrue(cc2LL.isLL1());
    }


}
