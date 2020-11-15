package lexer;

public class Token {
    ToKenType _type;
    String _value;

    public Token(ToKenType type, String value) {
        this._type = type;
        this._value = value;
    }

    public ToKenType getType() {
        return this._type;
    }

    @Override
    public String toString() {
        return String.format("type %s,value %s", _type, _value);
    }

    public boolean isVariable() {
        return _type == ToKenType.VARIABLE;
    }

    public boolean isScalar() {
        return _type == ToKenType.INTEGER || _type == ToKenType.FLOAT ||
                _type == ToKenType.STRING || _type == ToKenType.BOOLEAN;
    }

}
