package common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * 把字符串变为可迭代
 * @param <T>
 */
public class PeekIterator<T> implements Iterator<T> {

    private static final int CACHE_SIZE = 10;
    private final Iterator<T> it;
    private final LinkedList<T> queueCache = new LinkedList<>();
    private final LinkedList<T> statPutBacks = new LinkedList<>();
    private T _endToken;


    public PeekIterator(Stream<T> stream) {
        it = stream.iterator();
    }
    public PeekIterator(Stream<T> stream, T endToKen) {
        it = stream.iterator();
        _endToken = endToKen;

    }

    public T peek() {
        if (this.statPutBacks.size() > 0) {
            return this.statPutBacks.getFirst();
        }
        if (!it.hasNext()) {
            return _endToken;
        }
        T val = next();
        putBack();
        return val;
    }

    /**
     * A -> B -> C -> D
     * D -> C -> B -> A
     */
    public void putBack() {
        if (queueCache.size() > 0) {
            this.statPutBacks.push(queueCache.pollLast());
        }
    }

    @Override
    public boolean hasNext() {
        return  _endToken!=null ||
                this.statPutBacks.size()>0 ||
                it.hasNext();
    }

    @Override
    public T next() {

        T val;

        if (this.statPutBacks.size() > 0) {
            val = this.statPutBacks.pop();
        } else {
            if (!it.hasNext()){
                T tmp = _endToken;
                _endToken=null;
                return tmp;
            }
            val = it.next();
        }

        // 缓存最近的10个 , 这里缓存 9个
        while (queueCache.size() > CACHE_SIZE - 1) {
            // 过大则移除
            queueCache.poll();
        }
        // 在最后添加 1 个
        queueCache.add(val);

        return val;
    }
}
