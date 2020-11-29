package parser;

/**
 * 因子,操作符两边可以被用来计算的,如 变量,四则运算,布尔值等
 */
public abstract class Factor  extends ASTNode {
    public Factor(ASTNode _parent, ASTNodeTypes _type, String _label) {
        super(_parent, _type, _label);
    }
}
