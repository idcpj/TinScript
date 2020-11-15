package common;



import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PeekIteratorTest {

    @Test
    public void test_peak(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char) c), (char) 0);
        Character next = it.next();

        assertEquals('a', next.charValue());
        assertEquals('b',it.next().charValue());

        it.next();
        it.next();

        assertEquals('e',it.next().charValue());
        assertEquals('f',it.peek().charValue());
        assertEquals('f',it.peek().charValue());
        assertEquals('f',it.next().charValue());
        assertEquals('g',it.next().charValue());
    }
}