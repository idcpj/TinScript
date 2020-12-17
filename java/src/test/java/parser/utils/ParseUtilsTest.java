package parser.utils;

import common.PeekIterator;
import lexer.Lexer;
import lexer.LexicalException;
import lexer.Token;
import org.junit.Test;
import parser.ast.ASTNode;
import parser.ast.Expr;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParseUtilsTest {
    @Test
    public void simple() throws LexicalException, ParseException {
        ASTNode expr = createExpr("1+1+1");
        String actual = ParseUtils.toPostfixExpression(expr);

        System.out.println(actual);

        assertEquals("1 1 1 + +", actual);
    }

    @Test
    public void simple1() throws LexicalException, ParseException {

        ASTNode expr = createExpr("\"1\"==\"\"");

        assertEquals("\"1\" \"\" ==",ParseUtils.toPostfixExpression(expr));
    }

    @Test
    public void complex() throws LexicalException, ParseException {
        ASTNode expr1 = createExpr("1+2*3");
        ASTNode expr2 = createExpr("1*2+3");
        ASTNode expr3 = createExpr("10*(7+4)");
        ASTNode expr4 = createExpr("(1*2!=7)==3!=4*5+6");
        assertEquals("1 2 3 * +",ParseUtils.toPostfixExpression(expr1));
        assertEquals("1 2 * 3 +",ParseUtils.toPostfixExpression(expr2));
        assertEquals("10 7 4 + *",ParseUtils.toPostfixExpression(expr3));
        assertEquals("1 2 * 7 != 3 4 5 * 6 + != ==",ParseUtils.toPostfixExpression(expr4));
    }

    private ASTNode createExpr(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(src);
        PeekTokenIterator it = new PeekTokenIterator(tokens.stream());
        return Expr.parse(it);


    }
}