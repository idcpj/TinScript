package lexer;

import common.PeekIterator;
import common.PeekIteratorTest;
import jdk.nashorn.internal.parser.TokenType;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenTest {

    @Test
    public void test_makeVarOrKeyWord() {
        PeekIterator<Character> it1 = new PeekIterator<>("if abc".chars().mapToObj(x -> (char) x));
        PeekIterator<Character> it2 = new PeekIterator<>("true abc".chars().mapToObj(x -> (char) x));

        Token token1 = Token.makeVarOrKeyWord(it1);
        Token token2 = Token.makeVarOrKeyWord(it2);

        assertToken(token1, ToKenType.KEYWORD, "if");
        assertToken(token2, ToKenType.BOOLEAN, "true");

        it1.next();

        Token token3 = Token.makeVarOrKeyWord(it1);
        assertToken(token3, ToKenType.VARIABLE,"abc");

    }

    private void assertToken(Token token1, ToKenType keyword, String s) {
        assertEquals(keyword, token1.getType());
        assertEquals(s, token1.getValue());
    }

}