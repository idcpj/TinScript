package parser.utils;

import parser.ast.ASTNode;

public class ParseUtils {

    /**
     * 后续排列验证
     * @param node
     * @return
     */
    public static String toPostfixExpression(ASTNode node){
        String leftStr ="";
        String rightStr = "";
        switch (node.getType()){
            case BINARY_EXPR:
                leftStr =toPostfixExpression(node.getChild(0));
                rightStr = toPostfixExpression(node.getChild(1));
                return leftStr+" "+rightStr+" " +node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();
        }

        throw new RuntimeException("not implement");
    }
}
