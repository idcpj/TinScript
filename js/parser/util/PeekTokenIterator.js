const ParseException = require("./ParseException");
const PeekIterator =require("../../common/PeekIterator")
class PeekTokenIterator extends PeekIterator{
    constructor(it) {
        super(it);
    }

    nextMatch(value){
        let token = this.next()
        if (token.getValue() !==value){
            throw new ParseException.formToken(token)
        }
        return token
    }

    nextMatch1(type){
        let token = this.next()
        if (token.getType() !==type){
            throw new ParseException.formToken(token)
        }
        return token
    }

}

module.exports=PeekTokenIterator;