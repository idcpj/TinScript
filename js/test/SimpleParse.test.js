const {assert } = require("chai")
const SimpleParse =require("../parser/SimpleParse")
const Lexer =require("../lexer/Lexer")
const PeekTokenIterator = require("../parser/util/PeekTokenIterator")
const arrayToGenerator = require("../common/arrayToGenerator")

describe("SimpleParse",()=>{

    it('basic',  () =>{
        const source = "1+2+3+4"
        const lexer = new Lexer();
        const tokenArr = lexer.analyseString(source);
        const it = new PeekTokenIterator(arrayToGenerator(tokenArr))

        const e1 = SimpleParse.parse(it);
        assert.equal(e1.getChildren().length,2)
        assert.equal(e1.getLexeme().getValue(),"+")

        const  v1 =e1.getChild(0)
        const  e2 =e1.getChild(1)
        assert.equal(v1.getLexeme().getValue(),"1")
        assert.equal(e2.getLexeme().getValue(),"+")

        const v2 = e2.getChild(0)
        const e3 = e2.getChild(1)
        assert.equal(v2.getLexeme().getValue(),"2")
        assert.equal(e3.getLexeme().getValue(),"+")

        const v3 = e3.getChild(0)
        const v4 = e3.getChild(1)
        assert.equal(v3.getLexeme().getValue(),"3")
        assert.equal(v4.getLexeme().getValue(),"4")

        e1.print()


    });
})