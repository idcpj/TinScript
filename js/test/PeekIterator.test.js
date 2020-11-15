const PeekIterator = require("../common/PeekIterator")
const arrayToGenerator = require("../common/arrayToGenerator")

const {assert } = require("chai")

describe('test PeekIterator',()=>{
    it('test_peek',  ()=> {
        // [..."abcdefg"]   字符串转字节数组
        const iter = new PeekIterator(arrayToGenerator([..."abcdefg"]))
        assert.equal(iter.next(),'a')
        assert.equal(iter.next(),'b')
        assert.equal(iter.peek(),'c')
        assert.equal(iter.peek(),'c')
        assert.equal(iter.next(),'c')
        assert.equal(iter.next(),'d')
    })

    it('test_lookahead2', ()=> {
        const iter = new PeekIterator(arrayToGenerator([..."abcdefg"]))
        assert.equal(iter.next(),'a')
        assert.equal(iter.peek(),'b')
        assert.equal(iter.peek(),'b')
        assert.equal(iter.next(),'b')
        assert.equal(iter.next(),'c')
        iter.putBack()
        iter.putBack()
        assert.equal(iter.next(),'b')
        assert.equal(iter.next(),'c')
    });

    it('test_endToken',  ()=> {
        const iter = new PeekIterator(arrayToGenerator([..."abcdefg"]),'\0')
        for (let i = 0; i < 8; i++) {
            if (i===7){
                assert.equal(iter.next(),'\0')
            }else{
                assert.equal(iter.next(),"abcdefg"[i])
            }
        }
    });
})


