package pp.block2.cc.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import org.antlr.v4.runtime.tree.*;
import pp.block2.cc.*;
import pp.block2.cc.ll.Sentence;

import pp.block2.cc.antlr.SentenceParser.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class SentenceConverter
        extends SentenceBaseListener implements Parser {
    /**
     * Factory needed to create terminals of the {@link Sentence}
     * grammar. See {@link pp.block2.cc.ll.SentenceParser} for
     * example usage.
     */
    private final SymbolFactory fact;

    private Class<? extends Lexer> lexerType = Sentence.class;


    private static final NonTerm SENTENCE = new NonTerm("Sentence");
    private static final NonTerm SUBJECT = new NonTerm("Subject");
    private static final NonTerm OBJECT = new NonTerm("Object");
    private static final NonTerm MODIFIER = new NonTerm("Modifier");

    private ParseTreeProperty<AST> asts;

    private int errorNos;

    public SentenceConverter() {
        this.fact = new SymbolFactory(Sentence.class);
    }

    @Override
    public AST parse(Lexer lexer) throws ParseException {
        asts = new ParseTreeProperty<>();
        errorNos = 0;
        SentenceParser parser = new SentenceParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.sentence();
        new ParseTreeWalker().walk(this, tree);
        if (this.errorNos > 0){
            throw new ParseException();
        }
        return this.asts.get(tree); // TODO Fill in
    }

    @Override
    public void exitObject(ObjectContext ctx) {
        super.exitObject(ctx);
        generateAST(ctx, OBJECT);
    }

    @Override
    public void exitModifier(ModifierContext ctx) {
        super.exitModifier(ctx);
        generateAST(ctx, MODIFIER);
    }


    @Override
    public void exitSentence(SentenceContext ctx) {
        super.exitSentence(ctx);
        generateAST(ctx, SENTENCE);
    }

    @Override
    public void exitSubject(SubjectContext ctx) {
        super.exitSubject(ctx);
        generateAST(ctx, SUBJECT);

    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        setAST(node, this.fact.getTerminal(node.getSymbol().getType()));

    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
        this.errorNos++;
    }


    private AST getAST(ParseTree tree) {
        return this.asts.get(tree);
    }

    private void setAST(TerminalNode node, Term term) {
        this.asts.put(node, new AST(term, node.getSymbol()));
    }

    private void generateAST(ParseTree tree, NonTerm nonTerm) {
        AST ast = new AST(nonTerm);
        for (int i = 0; i < tree.getChildCount(); i++) {
            ast.addChild(getAST(tree.getChild(i)));
        }
        this.asts.put(tree,ast);
    }

    private Lexer scan(String text) {
        Lexer result = null;
        CharStream stream = CharStreams.fromString(text);
        try {
            Constructor<? extends Lexer> lexerConstr = this.lexerType
                    .getConstructor(CharStream.class);
            result = lexerConstr.newInstance(stream);
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            // should never occur, as all Antlr-generated lexers are
            // well-behaved
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        SentenceConverter converter = new SentenceConverter();
        try {
            converter.parse(converter.scan(("students love students.")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        converter.parse(converter.scan(("all undergraduate students love all compilers.")));
//        converter.parse(converter.scan(("all smart students love all compilers")));
//        converter.parse(converter.scan(("undergraduate students love love.")));
//        converter.parse(converter.scan(("all undergraduate students all compilers.")));
    }

    // From here on overwrite the listener methods
    // Use an appropriate ParseTreeProperty to
    // store the correspondence from nodes to ASTs
}
