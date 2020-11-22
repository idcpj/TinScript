const {assert}  =require("chai")

const arrayToGenerator  =require("../common/arrayToGenerator")
const PeekIterator  =require("../common/PeekIterator")
const TokenType  =require("../lexer/TokenType")
const Token = require("../lexer/Token");

describe('Token', ()=> {

    function assertToken(token,type,value){
        assert.equal(token.getType(),type)
        assert.equal(token.getValue(),value)
    }

    it('varOrKeyWord',  ()=> {

        let it1 = new PeekIterator(arrayToGenerator([..."if abc"]))
        let it2 = new PeekIterator(arrayToGenerator([..."true abc"]))

        let token1 = Token.makeVarOrKeyWord(it1);
        let token2 = Token.makeVarOrKeyWord(it2);

        it1.next()
        let token3 = Token.makeVarOrKeyWord(it1);

        assertToken(token1,TokenType.KEYWORD,"if")
        assertToken(token2,TokenType.BOOLEAN,"true")
        assertToken(token3,TokenType.VARIABLE,"abc")
    });

    it('makeString', function () {
        let arr=[
            `"123"`,
            `'123'`,
            `'abc'`,
            `''`,
            `""`,
        ]
        let res=[
            `"123"`,
            `'123'`,
            `'abc'`,
            `''`,
            `""`,
        ]

        for (let i = 0; i < arr.length; i++) {
            let it = new PeekIterator(arrayToGenerator([...arr[i]]));
            let token = Token.makeString(it);
            assertToken(token,TokenType.STRING,res[i])
        }
    });

    it('makeOp',  () => {

        const tests=[
            ["+ xxx","+"],
            ["++mmm","++"],
            ["/=g","/="],
            ["==1","=="],
            ["&=3982","&="],
            ["&777","&"],
            ["||xxx","||"],
            ["^=111","^="],
            ["%7","%"],
        ]

        for (let test of tests) {
            const [input,expected] =test
            let it = new PeekIterator(arrayToGenerator([...input]));
            let token = Token.makeOp(it);
            assertToken(token,TokenType.OPERATOR,expected);
        }
    });

    it('makeNumber', ()=> {
        let tests=[
            ['0.12asd','0.12'],
            ['+0.12asd','+0.12'],
            ['-0.12asd','-0.12'],
            ['+12asd','+12'],
            ['.12asd','.12'],
        ];

        for (const test of tests) {
            const [input,expected]=test
            let it = new PeekIterator(arrayToGenerator([...input]));
            let token = Token.makeNumber(it);

            let tokenType = token.getValue().indexOf(".")===-1?TokenType.INTEGER: TokenType.FLOAT;
            assertToken(token,tokenType,expected)
        }
    });
})