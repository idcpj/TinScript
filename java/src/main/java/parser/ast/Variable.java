package parser.ast;


import common.PeekIterator;
import lexer.Token;

public class Variable extends Factor  {
    public Variable(ASTNode _parent, PeekIterator<Token> it) {
        super(_parent, it);
    }
}
