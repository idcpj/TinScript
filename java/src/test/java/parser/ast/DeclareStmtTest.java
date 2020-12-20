package parser.ast;

import common.PeekIterator;
import lexer.Lexer;
import lexer.LexicalException;
import lexer.Token;
import org.junit.Test;
import parser.utils.ParseException;
import parser.utils.PeekTokenIterator;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeclareStmtTest {

    @Test
    public void declare() throws LexicalException, ParseException {
        PeekTokenIterator it = (PeekTokenIterator) createTokenIt("var i = 100* 2");
        ASTNode stmt = DeclareStmt.parse(null,  it);
        stmt.print(0);
    }

    private PeekIterator<Token> createTokenIt(String s) throws LexicalException {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(s);
        return new PeekTokenIterator(tokens.stream());
    }
}