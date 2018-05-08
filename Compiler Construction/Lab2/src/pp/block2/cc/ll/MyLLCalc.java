package pp.block2.cc.ll;

import java.util.*;

import pp.block2.cc.NonTerm;
import pp.block2.cc.Symbol;
import pp.block2.cc.Term;

public class MyLLCalc implements LLCalc {
    private Grammar grammar;
    private Map<Symbol, Set<Term>> first;
    private Map<NonTerm,Set<Term>> follow;
    private Map<Rule, Set<Term>> firstp;

    public MyLLCalc(Grammar grammar) {
        this.grammar = grammar;
        calcFirst();
        calcFollow();
        calcFirstp();
    }

    private void calcFirst() {
        first = new HashMap<>();

        Set<Term> eofSet = new HashSet<>();
        eofSet.add(Symbol.EOF);
        first.put(Symbol.EOF, eofSet);

        Set<Term> epsilonSet = new HashSet<>();
        epsilonSet.add(Symbol.EMPTY);
        first.put(Symbol.EMPTY, epsilonSet);

        //Add Terminals into set
        for (Term term : grammar.getTerminals()) {
            Set<Term> termSet = new HashSet<>();
            termSet.add(term);
            first.put(term, termSet);
        }

        for (NonTerm nonTerm : grammar.getNonterminals()) {
            first.put(nonTerm, new HashSet<>()); //nonTermSet is empty as always.
        }

        boolean iterate = true;
        while (iterate) {
            iterate = false;

            for (Rule rule : grammar.getRules()) {
                List<Symbol> beta = rule.getRHS();

                Set<Term> betaElementZero = first.get(beta.get(0)); //Get the terms of b0
                Set<Term> rhs = new HashSet<>(betaElementZero); //
                //rhs collect FIRST for lhs (of a specific token)
                rhs.remove(Symbol.EMPTY);
                //except empty

                int i = 0;
                while (i < beta.size() - 1 && first.get(beta.get(i)).contains(Symbol.EMPTY)) {
                    Set<Term> betaPlusOne= first.get(beta.get(i + 1));
                    rhs.addAll(betaPlusOne); //rhs < rhs UNION first (betai+1)
                    rhs.remove(Symbol.EMPTY); //deduct empty
                    i++;
                }

                if (i == beta.size() - 1 && first.get(beta.get(i)).contains(Symbol.EMPTY)) {
                    //Last symbol is still empty
                    rhs.add(Symbol.EMPTY);
                }

                int oldLength = first.get(rule.getLHS()).size();
                first.get(rule.getLHS()).addAll(rhs); //put that to the rule!
                iterate = iterate || oldLength < first.get(rule.getLHS()).size();
            }
        }
    }

    private void calcFollow() {
        follow = new HashMap<>();

        for (NonTerm nonTerm : grammar.getNonterminals()) {
            follow.put(nonTerm, new HashSet<>());
        }

        Set<Term> eofSet = new HashSet<>();
        eofSet.add(Symbol.EOF);
        follow.put(grammar.getStart(), eofSet);

        boolean iterate = true;
        while (iterate) {
            iterate = false;

            for (Rule rule : grammar.getRules()) {
                Set<Term> trailer = new HashSet<>(follow.get(rule.getLHS()));

                List<Symbol> beta = rule.getRHS();
                for (int i = beta.size() - 1; i >= 0; i--) {
                    if (beta.get(i) instanceof NonTerm) {
                        int oldLength = follow.get(beta.get(i)).size();
                        follow.get(beta.get(i)).addAll(trailer);
                        iterate = iterate || oldLength < follow.get(beta.get(i)).size();
                        if (first.get(beta.get(i)).contains(Symbol.EMPTY)) {
                            trailer.addAll(first.get(beta.get(i)));
                            trailer.remove(Symbol.EMPTY);
                        } else {
                            trailer = new HashSet<>(first.get(beta.get(i)));
                        }
                    } else {
                        trailer = new HashSet<>(first.get(beta.get(i)));
                    }
                }
            }
        }
    }

    private void calcFirstp() {
        firstp = new HashMap<>();
        for (Rule rule : grammar.getRules()) {
            Set<Term> firstSymbols = new HashSet<>(first.get(rule.getRHS().get(0)));
            int i = 1;
            while (i < rule.getRHS().size() - 1 && first.get(rule.getRHS().get(i)).contains(Symbol.EMPTY)) {
                firstSymbols.addAll(first.get(rule.getRHS().get(i)));
                i++;
            }

            firstp.put(rule, firstSymbols);
            if (firstSymbols.contains(Symbol.EMPTY)) {
                firstp.get(rule).addAll(follow.get(rule.getLHS()));
            }
        }
    }

    @Override
    public Map<Symbol, Set<Term>> getFirst() {
        return first;
    }

    @Override
    public Map<NonTerm, Set<Term>> getFollow() {
        return follow;
    }

    @Override
    public Map<Rule, Set<Term>> getFirstp() {
        return firstp;
    }

    @Override
    public boolean isLL1() {
        for (NonTerm nonTerm : grammar.getNonterminals()) {
            for (Rule rule : grammar.getRules(nonTerm)) {
                for (Rule rule2 : grammar.getRules(nonTerm)) {
                    if (rule != rule2) {
                        Set<Term> intersection = new HashSet<>(firstp.get(rule));
                        intersection.retainAll(firstp.get(rule2));
                        if (!intersection.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
