package parser;

public class Block extends Stmt {
    public Block(ASTNode _parent, String _label) {
        super(_parent, ASTNodeTypes.BLOCK, "block");
    }


}
