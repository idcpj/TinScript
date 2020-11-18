package lexer;

public class LexicalException extends Exception {
    private String msg;

    public LexicalException(char c) {
        String msg = String.format("Unexpected charator %c", c);
    }

    public LexicalException(String _msg) {
        msg = _msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
