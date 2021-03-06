package parser.ast;

import lexer.Token;
import parser.utils.ExprHOF;
import parser.utils.ParseException;
import parser.utils.PeekTokenIterator;
import parser.utils.PriorityTable;

import java.util.function.UnaryOperator;

// 表达式
public class Expr extends  ASTNode {

    private  static final PriorityTable table = new PriorityTable();

    public Expr(ASTNode parent){
        super(parent);
    }

    public Expr(ASTNode parent, ASTNodeTypes type, Token lexeme) {
        super(parent);
        this.type=type;
        this.lexeme=lexeme;
        this.label=lexeme.getValue();

    }
    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        return E(null,0,it);
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        return E(parent,0,it);
    }
    // 左递归 E(k) -> E(k) op(k) E(k+1) |E(k+1)
    // 转为有右递归:
    //  E(k) -> E(k+1) E_(k) // var e =new Expr(); e.left = E(k+1);e.op=op(k);e.right=E(k+1)E_(k)
    //  E_(k) -> op(k) E(k+1) E_(k) | ε (标识为空)
    // 最高优先级:
    //  E(t) -> F E_(k) | U E_(k)
    //  E_(t) -> op(t) E(t) E_(t) | ε
    public static ASTNode E(ASTNode parent, int k, PeekTokenIterator it) throws ParseException {

        if (k< table.size() - 1){
            return combine(parent,it,()->E(parent,k+1,it),()->E_(parent,k,it));
        }else{
            return race(
                    it,
                    ()->combine(parent,it,()->U(parent,it),()->E_(parent,k,it)),
                    ()->combine(parent,it,()->F(parent,it),()->E_(parent,k,it))
                    );
        }

    }

    private static ASTNode F(ASTNode parent, PeekTokenIterator it) {
        Token token = it.peek();
        if (token.isVariable()){
            return new Variable(parent,it);
        }else{
            return new Scalar(parent,it);
        }
    }

    private static ASTNode U(ASTNode parent, PeekTokenIterator it) throws ParseException {
        Token token  = it.peek();
        String value = token.getValue();

        if (value.equals("(")){
            it.nextMatch("(");
            ASTNode expr = E(parent,0,it);
            it.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")){
            Token t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(parent,ASTNodeTypes.UNARY_EXPR,t);
            unaryExpr.addChild(E(parent,0,it));
            return unaryExpr;
        }
        return null;
    }



    private static ASTNode E_(ASTNode parent, int k,PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value  = token.getValue();

        if (table.get(k).contains(value)){
            Expr expr =new Expr(parent,ASTNodeTypes.BINARY_EXPR,it.nextMatch(value));
            expr.addChild(combine(parent,it,
                    ()-> E(parent,k+1,it),
                    ()-> E_(parent,k,it))
            );
            return expr;

        }
        return null;
    }

    // 高级函数用于延迟加载
    private static ASTNode combine(ASTNode parent,PeekTokenIterator it,ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        ASTNode a = aFunc.hoc();
        if (a==null){
            return it.hasNext()?bFunc.hoc():null;
        }
        ASTNode b = it.hasNext()?bFunc.hoc():null;
        if (b==null){
            return a;
        }
        Expr expr =new Expr(parent,ASTNodeTypes.BINARY_EXPR,b.lexeme);
        expr.addChild(a);
        expr.addChild(b.getChild(0));

        return expr;

    }

    private static ASTNode race(PeekTokenIterator it,ExprHOF aFunc,ExprHOF bFunc) throws ParseException {
        if (!it.hasNext()){
            return null;
        }
        ASTNode a = aFunc.hoc();
        if (a!=null){
            return a;
        }
        return bFunc.hoc();

    }

}
