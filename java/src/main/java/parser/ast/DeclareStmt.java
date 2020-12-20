package parser.ast;


import lexer.Token;
import parser.utils.ParseException;
import parser.utils.PeekTokenIterator;

public class DeclareStmt extends Stmt {
    public DeclareStmt(ASTNode _parent) {
        super(_parent, ASTNodeTypes.DECLARE, "declare");
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        DeclareStmt stmt = new DeclareStmt(parent);
        it.nextMatch("var");
        Token tkn = it.peek();
        ASTNode factor = Factor.parse(parent, it);
        if (factor == null){
            throw new ParseException(tkn);
        }
        stmt.addChild(factor);
        Token lexeme = it.nextMatch("=");
        ASTNode expr = Expr.parse(parent, it);
        stmt.addChild(expr);
        stmt.setLexeme(lexeme);
        ;
        return stmt;
    }

}
