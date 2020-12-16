package parser.utils;

import parser.ast.ASTNode;

// HOF : high order function
// 作用 : 把函数传入函数
@FunctionalInterface
public interface ExprHOF {
    ASTNode hoc() throws ParseException;
}
