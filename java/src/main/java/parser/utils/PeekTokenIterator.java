package parser.utils;

import common.PeekIterator;
import lexer.Token;
import lexer.TokenType;

import java.util.stream.Stream;

public class PeekTokenIterator  extends PeekIterator<Token> {
    public PeekTokenIterator(Stream<Token> stream) {
        super(stream);
    }


    public PeekTokenIterator(Stream<Token> stream, Object endToKen) {
        super(stream, (Token) endToKen);
    }

    public Token nextMatch(String value) throws ParseException {
        Token token = this.next();
        if (!token.getValue().equals(value)){
            throw new ParseException(token);
        }
        return token;
    }

    public Token nextMatch(TokenType type) throws ParseException {
        Token token =this.next();
        if (token.getType()==type){
            throw new ParseException(token);
        }
        return token;
    }
}
