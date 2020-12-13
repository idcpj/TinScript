const ASTNode =require("./ASTNode")
const ASTNodeTypes = require("../../lexer/TokenType");
const TokenType = require("../../lexer/TokenType")

class Factor extends ASTNode{
    constructor(parent,it) {
        super(parent);

        const token = it.next();
        let type = token.getType();

        if (type===TokenType.VARIABLE){
            this.type = ASTNodeTypes.VARIABLE
        } else {
            this.type = ASTNodeTypes.SCALAR
        }

        this.label = token.getValue();
        this.lexeme = token

    }
}


module.exports=Factor