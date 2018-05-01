package pp.block1.cc.test;

import org.junit.Test;
import pp.block1.cc.antlr.PLI;

public class PLITest {
    private static LexerTester tester = new LexerTester(PLI.class);

    @Test
    public void test1() {
        tester.wrong("1");
//        tester.correct("");
        tester.correct("\"a\"");
        tester.correct("\"This is good!\" \"This is good!\"");
        tester.wrong("abcd");
        tester.wrong(" \"This is bad!");
    }

}
