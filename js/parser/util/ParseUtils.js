const ASTNodeType =require("../ast/ASTNodeTypes")

class ParseUtils {
    // postfix :后缀
    static  toPostfixExpression(astNode){
        switch (astNode.type) {
            case ASTNodeType.BINARY_EXPR:
                return `${ParseUtils.toPostfixExpression(astNode.getChild(0))} ${ParseUtils.toPostfixExpression(astNode.getChild(1))} ${ParseUtils.toPostfixExpression(astNode.lexeme.getValue())}`
            case ASTNodeType.VARIABLE:
            case ASTNodeType.SCALAR:
                return astNode.lexeme.getValue()
        }

        throw "not impl."
    }
}