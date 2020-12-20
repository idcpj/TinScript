const  ASTNode =require("./ASTNode")
const ASTNodeType = require("./ASTNodeTypes");
const table = require("../util/PriorityTable")
const Variable = require("./Variable")
const Scalar =require("./Scalar")

class Expr extends ASTNode{

    constructor(parent) {
        super(parent);
    }

    static fromToken(parent,type,token){
        const expr = new Expr(parent);
        expr.label=token.getValue();
        expr.lexeme = token
        return expr
    }

    /**
     *
     * @param parent
     * @param it
     */
    static parseExpr(parent,it){
        return Expr.E(parent,it,0)
    }
    static E(parent,it,k){
        if(k<table.length-1){
            return Expr.combine(parent,it,
                ()=>Expr.E(parent,it,k+1),
                ()=>Expr.E_(parent,it,k),
            )
        }else{
            return Expr.race(it,
                () => Expr.combine(
                    parent,
                    it,
                    () => Expr.U(parent, it),
                    () => Expr.E_(parent, it, k)
                ),
                () => Expr.combine(
                    parent, it,
                    () => Expr.F(parent, it),
                    () => Expr.E_(parent, it, k),
                ))
        }
    }

    static E_(parent,it,k){
        const token = it.peek();
        const value = token.getValue();

        if (table[k].indexOf(value)!==-1){
            it.nextMatch(value)
            const  expr = new Expr(parent,ASTNodeType.BINARY_EXPR,token)
            expr.addChild(this.combine(parent, it,
                () => Expr.E(parent, it, k + 1),
                ()=>Expr.E_(parent,it,k),
            ))
            return expr
        }

        return null

    }

    static U(parent,it){
        const token = it.peek()
        const value =token.getValue();

        if(value ==="("){
            it.nextMatch(")")
            const  expr = Expr.parseExpr(parent,it)
            it.nextMatch(")")
            return expr
        }else if (value==="++" || value==="--" || value ==="!"){
            const t  =it.peek();
            it.nextMatch(value)
            const expr  = new Expr(parent,ASTNodeType.UNARY_EXPR,t)
            expr.addChild(Expr.parseExpr(parent,it))
            return expr
        }
        return null
    }

    static F(parent,it){
        const token = it.peek()

        if (token.isVariable()){
            return new Variable(parent,it);
        }else{
            return new Scalar(parent,it)
        }
    }

    static combine(parent,it,funcA,funcB){
        if(!it.hasNext()){
            return null
        }
        const a = funcA();
        if(a==null){
            return it.hasNext()?funcB():null
        }
        const b=it.hasNext()?funcB():null
        if (b==null){
            return a;
        }
        console.log(b);
        const expr =Expr.fromToken(parent,ASTNodeType.BINARY_EXPR,b.lexeme);
        expr.addChild(a)
        expr.addChild(b.getChild(0))
        return expr

    }

    static race(it,funcA,funcB){
        if(!it.hasNext()){
            return null;
        }
        const a =funcA();
        if(a!=null){
            return a;
        }
        return funcB();

    }
}

module.exports=Expr