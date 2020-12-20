const ASTNode =require("./ASTNode")

class Variable extends ASTNode{
    constructor(parent,it) {
        super(parent,it);
    }
}


module.exports=Variable