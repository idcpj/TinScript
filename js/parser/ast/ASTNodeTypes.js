const Enum  =require("../../common/Enum")


module.exports={
    BLOCK:new Enum("BLOCK",1),
    BINARY_EXPR:new Enum("BINARY_EXPR",1),
    UNARY_EXPR:new Enum("UNARY_EXPR",1),
    VARIABLE:new Enum("VARIABLE",1),
    SCALAR:new Enum("SCALAR",1),
    IF_STMT:new Enum("IS_STMT",1),
    WHILE_STMT:new Enum("WHILE_STMT",1),
    ASSIGN_STMT:new Enum("ASSIGN_STMT",1),
    FOR_STMT:new Enum("FOR_STMT",1),
    FUNCTION_DECLARE_STMT:new Enum("FUNCTION_DECLARE_STMT",1),
}