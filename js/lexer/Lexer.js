const PeekIterator =require("../common/PeekIterator")
const Token  =require("../lexer/Token")
const TokenType  =require("../lexer/TokenType")
const LexicalException = require("./LexicalException");
const AlphabetHelper = require("./AlphabetHelper");
const arrayToGenerator = require("../common/arrayToGenerator")
/**
 * 迭代器
 */
class Lexer{
    analyseString(source){
        let it = new PeekIterator(arrayToGenerator([...source]));
        return this.analyse(it);
    }

    analyse(it){
        const tokens = [];
        while (it.hasNext()){
            let c = it.next();
            if (c==='\0'){
                break;
            }
            let  lookahead = it.peek();

            if (c===' ' || c==='\n'){
                continue;
            }
            // 提取注释
            //   山航
            // /* */ 删多行
            if(c==='/'){
                if(lookahead==="/"){
                    while(it.hasNext()){
                        let c1 = it.next();
                        if(c1==='\n'){
                            break;
                        }
                    }
                }else if(lookahead==="*"){
                    let valid = false;
                    while (it.hasNext()){
                        let c1 = it.next();
                        if (c1==='*' && it.peek()==='/'){
                            valid=true;
                            it.next();
                            break;
                        }
                    }

                    if(!valid){
                        throw new LexicalException("comment not matched");
                    }
                }
                continue;
            }


            if(c==="{" || c==="}" || c==="(" || c===")"){
                tokens.push(new Token(TokenType.BRACKET,c));
                continue;
            }
            if (c===`'` ||c===`"`){
                it.putBack();
                tokens.push(Token.makeString(it));
                continue;
            }
            if (AlphabetHelper.isLetter(c)){
                it.putBack();
                tokens.push(Token.makeVarOrKeyWord(it));
                continue;
            }
            if (AlphabetHelper.isNumber(c)){
                it.putBack();
                tokens.push(Token.makeNumber(it));
                continue;
            }

            // 判断数字
            // 符合 +1 , 3*+1
            // 不符合 1+1,
            if( (c===`+`|| c===`-`) && AlphabetHelper.isNumber(lookahead)){
                const lastToken = tokens[tokens.length -1] || null
                if(lastToken ==null || !lastToken.isValue()){
                    it.putBack();
                    tokens.push(Token.makeNumber(it));
                    continue;
                }
            }

            if(AlphabetHelper.isOperator(c)){
                it.putBack();
                tokens.push(Token.makeOp(it));
                continue
            }

            throw new LexicalException.fromChar(c);

        }// end while

        return tokens;

    }
}

module.exports=Lexer