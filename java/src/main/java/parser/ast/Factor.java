package parser.ast;


import common.PeekIterator;
import lexer.Token;
import lexer.TokenType;
import org.omg.CORBA.portable.ValueInputStream;
import parser.utils.PeekTokenIterator;

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

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) {
        Token token = it.peek();
        TokenType type = token.getType();
        if (type == TokenType.VARIABLE){
            it.next();
            return new Variable(parent,it);
        }else if (token.isScalar()){
            it.next();
            return new Scalar(parent,it);
        }
        return null;
    }
}
