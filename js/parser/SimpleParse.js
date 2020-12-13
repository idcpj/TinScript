const Expr  =require("./ast/Expr")
const Scalar = require("./ast/Scalar")
const ASTNodeTypes = require("./ast/ASTNodeTypes");

class SimpleParse {

    // expr -> digit + expr | digit
    // digit -> 0|1|2|3...|9
    static parse(it){
        const  expr = new Expr(null);
        const scalar  =new Scalar(expr,it);

        if (!it.hasNext()){
            return scalar;
        }

        expr.lexeme=it.nextMatch("+")
        expr.label="+"
        expr.type=ASTNodeTypes.BINARY_EXPR

        expr.addChild(scalar)

        expr.addChild(this.parse(it))


        return expr
    }
}

module.exports = SimpleParse