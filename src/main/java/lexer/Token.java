package lexer;

import common.AlphabetHelper;
import common.PeekIterator;
import jdk.nashorn.internal.parser.TokenType;

import java.util.Objects;

public class Token {
    ToKenType _type;
    String _value;
    private long value;

    public Token(ToKenType type, String value) {
        _type = type;
        _value = value;
    }

    public ToKenType getType() {
        return _type;
    }

    public String getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return String.format("type %s,value %s", _type, _value);
    }

    public boolean isVariable() {
        return _type == ToKenType.VARIABLE;
    }

    public boolean isScalar() {
        return _type == ToKenType.INTEGER || _type == ToKenType.FLOAT ||
                _type == ToKenType.STRING || _type == ToKenType.BOOLEAN;
    }

    /**
     *
     * @param it
     * @return
     */
    public static Token makeVarOrKeyWord(PeekIterator<Character> it){
        String s="";

        // 获取一个字符串
        while (it.hasNext()){
            Character lookahead = it.peek();
            if (AlphabetHelper.isLetter(lookahead)){
                s+=lookahead;
            }else{
                break;
            }

            it.next();
        }

        // 判断是否是关键词
        if (KeyWords.isKeyWord(s)){
            return new Token(ToKenType.KEYWORD,s);
        }

        if (s.equals("true") || s.equals("false")){
            return new Token(ToKenType.BOOLEAN,s);
        }

        return new Token(ToKenType.VARIABLE,s);

    }


}
