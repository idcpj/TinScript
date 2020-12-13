package parser;

import common.PeekIterator;
import lexer.Token;
import parser.ast.ASTNode;
import parser.ast.ASTNodeTypes;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.utils.ParseException;
import parser.utils.PeekTokenIterator;

public class SimpleParser {

    /**
     * expr -> digit + expr | digit
     * digit -> 0|1|2|3|4|...|9
     *
     * @param it
     * @return
     */
    public static ASTNode parse(PeekTokenIterator it) throws ParseException {

        Expr expr = new Expr(null);
        Scalar scalar = new Scalar(expr, it);

        // 终止条件
        if (!it.hasNext()) {
            return scalar;
        }

        // 根节点
        expr.setLexeme(it.peek());
        it.nextMatch("+");// 判断下一个节点是否为 "+",不是则抛异常
        expr.setLabel("+");

        // 左节点
        expr.addChild(scalar);
        expr.setType(ASTNodeTypes.BINARY_EXPR);

        // 节点
        ASTNode rightNode = parse(it);
        expr.addChild(rightNode);

        return expr;

    }
}
