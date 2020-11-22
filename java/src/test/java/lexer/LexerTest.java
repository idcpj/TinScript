package lexer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;


public class LexerTest {

    private void assertToken(Token token1, TokenType keyword, String s) {
        assertEquals(keyword, token1.getType());
        String value = token1.getValue();
        assertEquals(s, value);
    }

    @Test
    public void test_analyse() throws LexicalException {
        Lexer lexer = new Lexer();
        String source ="(a+b)^100.12==+100-20";
        ArrayList<Token> tokens = null;
        tokens = lexer.analyse(source);

        assertNotNull(tokens);
        assertEquals(11,tokens.size());
        assertToken(tokens.get(0), TokenType.BRACKET, "(");
        assertToken(tokens.get(1), TokenType.VARIABLE, "a");
        assertToken(tokens.get(2), TokenType.OPERATOR, "+");
        assertToken(tokens.get(3), TokenType.VARIABLE, "b");
        assertToken(tokens.get(4), TokenType.BRACKET, ")");
        assertToken(tokens.get(5), TokenType.OPERATOR, "^");
        assertToken(tokens.get(6), TokenType.FLOAT, "100.12");
        assertToken(tokens.get(7), TokenType.OPERATOR, "==");
        assertToken(tokens.get(8), TokenType.INTEGER, "+100");
        assertToken(tokens.get(9), TokenType.OPERATOR, "-");
        assertToken(tokens.get(10), TokenType.INTEGER, "20");
    }

    @Test
    public  void  test_function() throws LexicalException {
        String source ="func foo(a,b){\n"+
                "print(a+b)\n"+
                "}\n"+
                "foo(-100.0,100)";
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(source);
        assertEquals(21,tokens.size());
        assertToken(tokens.get(0), TokenType.KEYWORD,"func" );
        assertToken(tokens.get(1), TokenType.VARIABLE,"foo" );
        assertToken(tokens.get(2), TokenType.BRACKET,"(" );
        assertToken(tokens.get(3), TokenType.VARIABLE,"a" );
        assertToken(tokens.get(4), TokenType.OPERATOR,"," );
        assertToken(tokens.get(5), TokenType.VARIABLE,"b" );
        assertToken(tokens.get(6), TokenType.BRACKET,")" );
        assertToken(tokens.get(7), TokenType.BRACKET,"{" );
        assertToken(tokens.get(8), TokenType.VARIABLE,"print" );
        assertToken(tokens.get(9), TokenType.BRACKET,"(" );
        assertToken(tokens.get(10), TokenType.VARIABLE,"a" );
        assertToken(tokens.get(11), TokenType.OPERATOR,"+" );
        assertToken(tokens.get(12), TokenType.VARIABLE,"b" );
        assertToken(tokens.get(13), TokenType.BRACKET,")" );
        assertToken(tokens.get(14), TokenType.BRACKET,"}" );
        assertToken(tokens.get(15), TokenType.VARIABLE,"foo" );
        assertToken(tokens.get(16), TokenType.BRACKET,"(" );
        assertToken(tokens.get(17), TokenType.FLOAT,"-100.0" );
        assertToken(tokens.get(18), TokenType.OPERATOR,"," );
        assertToken(tokens.get(19), TokenType.INTEGER,"100" );
        assertToken(tokens.get(20), TokenType.BRACKET,")" );

    }

    @Test
    public  void test_deleteComment() throws LexicalException{
        String source = "/*12321312\n123123213*/a=1";
        Lexer lexer =new Lexer();
        ArrayList<Token> tokens = lexer.analyse(source);
        assertEquals(3,tokens.size());
    }
}