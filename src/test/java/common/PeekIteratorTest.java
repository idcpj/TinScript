package common;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PeekIteratorTest {

    @Test
    public void test_peak() {
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char) c), (char) 0);

        assertEquals('a', it.next().charValue());
        assertEquals('b', it.next().charValue());

        it.next();
        it.next();

        assertEquals('e', it.next().charValue());
        assertEquals('f', it.peek().charValue());
        assertEquals('f', it.peek().charValue());
        assertEquals('f', it.next().charValue());
        assertEquals('g', it.next().charValue());
    }
    @Test
    public  void test_lookahead2(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char) c),(char)0);

        assertEquals('a',it.next().charValue());
        assertEquals('b',it.next().charValue());
        assertEquals('c',it.next().charValue());
        it.putBack();
        it.putBack();
        assertEquals('b',it.next().charValue());
    }

    @Test
    public void test_endToken(){
        String source = "abcdefg";
        PeekIterator<Character> it = new PeekIterator<>(source.chars().mapToObj(c -> (char) c), (char) 0);
        int i =0;
        while(it.hasNext()){
            if (i==source.length()){
                assertEquals((char)0,it.next().charValue());
            }else{
                assertEquals(source.charAt(i),it.next().charValue());
                i++;
            }
        }
    }
}