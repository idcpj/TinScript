class ParseException  extends Error{
    constructor(msg) {
        super(msg);
    }

    static formToken(token){
        return new ParseException(`Syntax Error,unexpected token ${token.getValue()}`)
    }
}

module.exports=ParseException;