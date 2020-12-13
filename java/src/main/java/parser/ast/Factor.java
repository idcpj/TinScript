package parser.ast;


import common.PeekIterator;
import lexer.Token;
import lexer.TokenType;

/**
 * 因子,操作符两边可以被用来计算的,如 变量,四则运算,布尔值等
 */
public abstract class Factor extends ASTNode {

    public  Factor(ASTNode _parent, PeekIterator<Token> it) {
        super(_parent);
        Token token =it.next();
        TokenType type =token.getType();

        if (type == TokenType.VARIABLE){
            this.type =ASTNodeTypes.VARIABLE;
        } else {
            this. type = ASTNodeTypes.SCALAR;
        }

        this.label=token.getValue();
        this.lexeme=token;
    }
}
