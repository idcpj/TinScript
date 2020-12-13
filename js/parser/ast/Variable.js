const ASTNode =require("ASTNode")
const ASTNodeTypes =require("ASTNodeTypes")

class Variable extends ASTNode{
    constructor(parent,it) {
        super(parent,it);
    }
}


module.exports=Variable