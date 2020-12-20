const Lexer = require("../lexer/Lexer");
const Expr = require("../parser/ast/Expr");
const {assert} =require("chai")
const PeekTokenIterator =require("../parser/util/PeekTokenIterator")
const arrayToGenerator =require("../common/arrayToGenerator")
describe("test parserExpression",()=>{

    it('simple', ()=> {
        const expr = createExpr("1+1+1")
        assert.equal(ParseUtils.toPostfixExpression(expr),"1 1 1 + +")
    });
})


function createExpr(str){
    const lexer = new Lexer()
    const tokens = lexer.analyseString(str)
    const it = new PeekTokenIterator(arrayToGenerator(tokens))
    return Expr.parseExpr(null,it)
}