package parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.Test;
import parser.ast.ASTNode;
import parser.utils.ParseException;
import parser.utils.PeekTokenIterator;

import static org.junit.Assert.assertEquals;

public class SimpleParserTest {

    @Test
    public void test_simpleParse() throws LexicalException, ParseException {
        String source ="1+2+3+4";
        Lexer lexer = new Lexer();
        PeekTokenIterator it = new PeekTokenIterator(lexer.analyse(source).stream());

        ASTNode e1 = SimpleParser.parse(it);
        ASTNode v1 = e1.getChild(0);

        assertEquals(2,e1.getChildren().size());
        assertEquals("1", v1.getLexeme().getValue());
        assertEquals("+",e1.getLexeme().getValue());

        ASTNode e2 =e1.getChild(1);
        ASTNode v2 = e2.getChild(0);

        assertEquals("2",v2.getLexeme().getValue());
        assertEquals("+",e2.getLexeme().getValue());

        ASTNode e3 =e2.getChild(1);
        ASTNode v3 = e3.getChild(0);

        assertEquals("3",v3.getLexeme().getValue());
        assertEquals("+",e3.getLexeme().getValue());

        ASTNode v4 = e3.getChild(1);
        assertEquals("4",v4.getLexeme().getValue());

        e1.print(1);
    }
}