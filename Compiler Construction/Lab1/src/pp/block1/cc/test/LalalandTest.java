package pp.block1.cc.test;

import org.junit.Test;
import pp.block1.cc.antlr.Lalaland;

public class LalalandTest {
    private static LexerTester tester = new LexerTester(Lalaland.class); // TODO fill in once you have the ID6 grammar

    @Test
    public void test1(){
        tester.wrong("Laaaaaa LaLi");
        tester.wrong("Laaaaaaa aaaaa");
        tester.correct("Laaaaaaaaaaaa");
    }
}
