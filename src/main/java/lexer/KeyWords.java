package lexer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 关键词表
 */
public class KeyWords {
    static String[] keywords={
            "var",
            "if",
            "else",
            "for",
            "while",
            "break",
            "func",
            "return",
    };

    static HashSet<String> set = new HashSet<>(Arrays.asList(keywords));

    public static boolean isKeyWord(String word){
        return set.contains(word);
    }

}
