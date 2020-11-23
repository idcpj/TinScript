package lexer;

import common.PeekIterator;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * 从发分析器
 */
public class Lexer {


    public ArrayList<Token> analyse(String source)throws LexicalException{
        return analyse(source.chars().mapToObj(c->(char)c));
    }

    /**
     * 将多个状态机合成一个,并返回一个tokens
     * @param source
     * @return
     */
    public ArrayList<Token> analyse(Stream<Character> source) throws LexicalException {
        ArrayList<Token> tokens = new ArrayList<>();
        PeekIterator<Character> it = new PeekIterator<>(source,(char)0);

        while(it.hasNext()){
            char c =it.next();

            if (c==0){
                break;
            }

            // 获取下一次词
            Character lookaHead = it.peek();

            // 无用字符
            if (c==' ' || c=='\n'){
                continue;
            }

            // 删除注释
            if (c=='/'){
                if (lookaHead=='/'){
                    // 行注释
                    while (it.hasNext()  && (it.next() !='\n') );
                }else if (lookaHead =='*'){
                    boolean valid = false;
                    while (it.hasNext()){
                        char currChar = it.next();
                        char secondChar = it.peek();
                        if (currChar=='*' && secondChar=='/'){
                            it.next();
                            valid=true;
                            break;
                        }
                    }// end while

                    if (!valid){
                        throw new  LexicalException("comments not match");
                    }
                    continue;
                }
            }

            if (c=='{' || c=='}' || c=='(' || c==')'){
                tokens.add(new Token(TokenType.BRACKET,c+""));
                continue;
            }
            if (c=='"' || c=='\''){
                // 放回去,并交给字符串子程序
                it.putBack();
                tokens.add(Token.makeString(it));
                continue;
            }

            if (AlphabetHelper.isNumber(c)){
                it.putBack();
                tokens.add(Token.makeNumber(it));
                continue;
            }

            if (AlphabetHelper.isLiteral(c)){
                it.putBack();
                tokens.add(Token.makeVarOrKeyWord(it));
                continue;
            }



            // 情况一: 3+5,
            // 情况二: +5,
            // 情况三: 3 * +5,
            // 情况四: 3 * 3.5,
            // 如果前面为空或还是一个字符,则此操作符属于数字
            if ((c=='+' || c=='-' || c=='.') && AlphabetHelper.isNumber(lookaHead)){
                Token lastToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);
                if (lastToken==null || !lastToken.isNumber() || lastToken.isOperator() ){
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }
            }

            if (AlphabetHelper.isOperator(c)){
                it.putBack();
                tokens.add(Token.makeOp(it));
                continue;
            }


            throw new LexicalException(c);

        }
        return tokens;
    }

}
