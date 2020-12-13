package parser.ast;

import common.PeekIterator;
import lexer.Token;

public class Scalar extends Factor  {
    public Scalar(ASTNode _parent, PeekIterator<Token> it) {
        super(_parent,it);
    }
}
