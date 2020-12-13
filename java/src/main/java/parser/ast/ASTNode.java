package parser.ast;

import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    /*树*/
    protected ArrayList<ASTNode> children =new ArrayList<>();
    protected ASTNode parent;

    /*关键信息*/
    protected Token lexeme;
    protected String label;
    protected ASTNodeTypes type;


    public ASTNode(ASTNode _parent){
        this.parent = _parent;
    }
    public ASTNode(ASTNode _parent,ASTNodeTypes _type, String _label){
        this.parent=_parent;
        this.type=_type;
        this.label=_label;
    }

    public ASTNode getChild(int index){
        return this.children.get(index);
    }

    public void addChild(ASTNode node){
        children.add(node);
    }

    public Token getLexeme(){
        return lexeme;
    }

    public List<ASTNode> getChildren(){
        return children;
    }

    public void setLexeme(Token token){
        this.lexeme=token;
    }
    public void setLabel(String s){
        this.label=s;
    }

    public  void setType(ASTNodeTypes type){
        this.type=type;
    }

    public void print(int indent){
        System.out.println(StringUtils.leftPad(" ",indent*2)+label);
        for (ASTNode child:children){
            child.print(indent+1);
        }
    }


}