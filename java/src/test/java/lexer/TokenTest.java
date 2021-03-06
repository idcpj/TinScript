package lexer;

import common.PeekIterator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TokenTest {

    @Test
    public void test_makeVarOrKeyWord() {
        PeekIterator<Character> it1 = new PeekIterator<>("if abc".chars().mapToObj(x -> (char) x));
        PeekIterator<Character> it2 = new PeekIterator<>("true abc".chars().mapToObj(x -> (char) x));

        Token token1 = Token.makeVarOrKeyWord(it1);
        Token token2 = Token.makeVarOrKeyWord(it2);

        assertToken(token1, TokenType.KEYWORD, "if");
        assertToken(token2, TokenType.BOOLEAN, "true");

        it1.next();

        Token token3 = Token.makeVarOrKeyWord(it1);
        assertToken(token3, TokenType.VARIABLE, "abc");

    }

    private void assertToken(Token token1, TokenType keyword, String s) {
        assertEquals(keyword, token1.getType());
        String value = token1.getValue();
        assertEquals(s, value);
    }

    @Test
    public void test_makeString() {
        String[] tests = {
                "\"abc\"",
                "\'abc\'",
                "\"\"",
                "\'\'",
        };
        try {

            for (String test : tests) {
                PeekIterator<Character> it1 = new PeekIterator<>(test.chars().mapToObj(c -> (char) c));
                Token token = Token.makeString(it1);
                assertToken(token, TokenType.STRING,test);
            }

        } catch (LexicalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_makeOp() {
        String[] tests = {
                "+ xxx",
                "++mmm",
                "/=g",
                "==1",
                "&=3982",
                "&777",
                "||xxx",
                "^=111",
                "%7"
        };
        String[] results = {"+","++","/=","==","&=","&","||","^=","%"};

        try {

            for (int i = 0; i < tests.length; i++) {
                PeekIterator<Character> it = new PeekIterator<>(tests[i].chars().mapToObj(c->(char)c));
                Token token = Token.makeOp(it);
                assertToken(token, TokenType.OPERATOR,results[i]);
            }

        } catch (LexicalException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_makeNumber() {
        String[] tests={
                "2",
                "-2",
                "+0 aa",
                "-0 aa",
                ".3 ccc",
                ".555 ddd",
                "7789.8888 ddd",
                "-1000.123123*123123",
        };
        try {
            for (String test : tests) {
                PeekIterator<Character> it = new PeekIterator<>(test.chars().mapToObj(c -> (char) c),(char)0);
                Token token = Token.makeNumber(it);

                String[] split = test.split("[* ]+");
                String result = split[0];

                assertToken(token, result.contains(".") ? TokenType.FLOAT : TokenType.INTEGER, result);
            }
        } catch (LexicalException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}