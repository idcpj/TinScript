const ASTNode =require("./ASTNode")

class Stmt extends ASTNode{
    constructor(parent,type,value) {
        super(parent,type,value);

    }
}

module.exports=Stmt