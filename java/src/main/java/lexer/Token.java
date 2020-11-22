package lexer;

import common.AlphabetHelper;
import common.PeekIterator;

public class Token {
    TokenType _type;
    String _value;

    public Token(TokenType type, String value) {
        _type = type;
        _value = value;
    }

    public TokenType getType() {
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
        return _type == TokenType.VARIABLE;
    }

    public boolean isScalar() {
        return _type == TokenType.INTEGER || _type == TokenType.FLOAT ||
                _type == TokenType.STRING || _type == TokenType.BOOLEAN;
    }

    /**
     * char 串如果匹配关键字,则为关键词
     *        如果为true或 false,则为boolean 值
     *        其他则为变量
     * @param it
     * @return
     */
    public static Token makeVarOrKeyWord(PeekIterator<Character> it) {
        String s = "";

        // 获取一个字符串
        while (it.hasNext()) {
            Character lookahead = it.peek();
            if (AlphabetHelper.isLetter(lookahead)) {
                s += lookahead;
            } else {
                break;
            }

            it.next();
        }

        // 判断是否是关键词
        if (KeyWords.isKeyWord(s)) {
            return new Token(TokenType.KEYWORD, s);
        }

        if (s.equals("true") || s.equals("false")) {
            return new Token(TokenType.BOOLEAN, s);
        }

        return new Token(TokenType.VARIABLE, s);

    }


    /**
     * char 串中匹配 '...' or "..." 则是字符串
     * @param it
     * @return
     * @throws LexicalException
     */
    public static Token makeString(PeekIterator<Character> it) throws LexicalException {
        StringBuilder s = new StringBuilder();
        int state = 0;

        while (it.hasNext()) {
            char c = it.next();

            switch (state) {
                case 0:
                    if (c == '\'') {
                        state = 1;
                    } else if (c == '\"') {
                        state = 2;
                    }
                    s.append(c);
                    break;
                case 1:
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s.toString() + c);
                    } else {
                        s.append(c);
                    }
                    break;
                case 2:
                    if (c == '\"') {
                        return new Token(TokenType.STRING, s.toString() + c);
                    } else {
                        s.append(c);
                    }
                    break;
            }
        } // end while

        // 不可能到这里,但是为了 java的规范,添加一句
        throw new LexicalException("Unexpected error");
    }


    public static Token makeOp(PeekIterator<Character> it) throws LexicalException {
        int state = 0;

        while (it.hasNext()) {
            char lookahead = it.next();
            switch (state) {
                case 0:
                    switch (lookahead) {
                        case '+':
                            state = 1;
                            break;
                        case '-':
                            state = 2;
                            break;
                        case '*':
                            state = 3;
                            break;
                        case '/':
                            state = 4;
                            break;
                        case '>':
                            state = 5;
                            break;
                        case '<':
                            state = 6;
                            break;
                        case '=':
                            state = 7;
                            break;
                        case '!':
                            state = 8;
                            break;
                        case '&':
                            state = 9;
                            break;
                        case '|':
                            state = 10;
                            break;
                        case '^':
                            state = 11;
                            break;
                        case '%':
                            state = 12;
                            break;
                        case ',':
                            return new Token(TokenType.OPERATOR, ",");
                        case ';':
                            return new Token(TokenType.OPERATOR, ";");
                    }// while end
                    break;

                case 1:
                    if (lookahead=='+'){
                        return new Token(TokenType.OPERATOR,"++");
                    }else if (lookahead == '='){
                        return new Token(TokenType.OPERATOR,"+=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"+");
                    }

                case 2:
                    if (lookahead=='-'){
                        return new Token(TokenType.OPERATOR,"--");
                    }else if (lookahead == '='){
                        return new Token(TokenType.OPERATOR,"-=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"-");
                    }
                case 3:
                    if (lookahead == '='){
                        return new Token(TokenType.OPERATOR,"*=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"*");
                    }
                case 4:
                    if (lookahead == '='){
                        return new Token(TokenType.OPERATOR,"/=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"/");
                    }
                case 5:
                    if ( lookahead == '='){
                        return new Token(TokenType.OPERATOR,">=");

                    }else if (lookahead == '>'){
                        return new Token(TokenType.OPERATOR,">>");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,">");
                    }
                case 6:
                    if ( lookahead == '='){
                        return new Token(TokenType.OPERATOR,"<=");

                    }else if (lookahead == '<'){
                        return new Token(TokenType.OPERATOR,"<<");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"<");
                    }
                case 7:
                    if ( lookahead == '='){
                        return new Token(TokenType.OPERATOR,"==");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"=");
                    }
                case 8:
                    if ( lookahead == '='){
                        return new Token(TokenType.OPERATOR,"!=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"!");
                    }
                case 9:
                    if ( lookahead == '&'){
                        return new Token(TokenType.OPERATOR,"&&");
                    }else if (lookahead=='='){
                        return new Token(TokenType.OPERATOR,"&=");
                    }else{
                        it.putBack();
                        return new Token(TokenType.OPERATOR,"&");
                    }
                case 10:
                    if(lookahead == '|') {
                        return new Token(TokenType.OPERATOR, "||");
                    }  else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "|=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "|");
                    }
                case 11:
                    if(lookahead == '^') {
                        return new Token(TokenType.OPERATOR, "^^");
                    }  else if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "^=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "^");
                    }
                case 12:
                    if (lookahead == '=') {
                        return new Token(TokenType.OPERATOR, "%=");
                    } else {
                        it.putBack();
                        return new Token(TokenType.OPERATOR, "%");
                    }
            }
        }

        throw new LexicalException("Unexpected error");
    }

    public static Token makeNumber(PeekIterator<Character> it )throws LexicalException{
        StringBuilder s= new StringBuilder();
        int state =0;

        while(it.hasNext()){
            char  lookahead = it.peek();
            switch (state){
                case 0:
                    if (lookahead=='0'){
                        state=1;
                    }else if(AlphabetHelper.isNumber(lookahead)){
                        state=2;
                    }else if(lookahead=='+' || lookahead=='-' ){
                        state=3;
                    }else if(lookahead=='.' ){
                        state=5;
                    }
                    break;
                case 1:
                    if (lookahead =='0'){
                        state=1;
                    }else if (AlphabetHelper.isNumber(lookahead)){
                        state=2;
                    }else if(lookahead=='.'){
                        state=4;
                    }else{
                        return new Token(TokenType.INTEGER, s.toString());
                    }
                    break;
                case 2:
                    if (AlphabetHelper.isNumber(lookahead)){
                        state=2;
                    }else if (lookahead=='.'){
                        state=4;
                    }else{
                        return new Token(TokenType.INTEGER, s.toString());
                    }
                    break;
                case 3:
                    if (AlphabetHelper.isNumber(lookahead)){
                        state=2;
                    }else if(lookahead=='.'){
                        state=5;
                    }else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 4:
                    if (lookahead=='.'){
                        throw new LexicalException(lookahead);
                    }else if (AlphabetHelper.isNumber(lookahead)){
                        state=20;
                    }else{
                        return new Token(TokenType.FLOAT, s.toString());
                    }
                    break;
                case 5:
                    if (AlphabetHelper.isNumber(lookahead)){
                        state=20;
                    }else{
                        throw new LexicalException(lookahead);
                    }
                    break;

                case 20:
                    if (AlphabetHelper.isNumber(lookahead)){
                        state=20;
                    }else if (lookahead=='.'){
                        throw new LexicalException(lookahead);
                    }else {
                        return new Token(TokenType.FLOAT, s.toString());
                    }

            } // end switch
            it.next();
            s.append(lookahead);

        }// end while

        throw new LexicalException("Unexpected err ");

    }

    public boolean isNumber() {
        return _type==TokenType.INTEGER||_type==TokenType.FLOAT;
    }

    public boolean isOperator() {
        return _type==TokenType.OPERATOR;
    }
}
