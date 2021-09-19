package ru.cft.merger;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

import ru.cft.util.Logger;
import ru.cft.util.PeekingIterator;

/**
 *
 * @param <T> - merging element type, should implement {@link Serializable} and {@link Comparable}
 */
public class Merger<T extends Serializable&Comparable<T>> {
    /**
     * Class constructor. Initializes with default comparator
     */
    public Merger(){
        this.elementComparator = Comparator.naturalOrder();
    }

    /**
     * Class constructor, specifying the element comparator.
     * @param comparator - functor, comparing the elements during merge.
     */
    public Merger(Comparator<T> comparator){
        this.elementComparator = comparator;
    }

    /**
     * Merges supposed to be iterable objects into output stream
     * If an iterable is not sorted, the result will be partial sorted stream.
     * @param mergingIterables - supposed-to-be sorted iterables.
     * @param mergedStream - output stream, where merged elements will be written.
     * @throws IOException - if an I/O error occurs
     */
    public void mergeIterables(List<? extends Iterable<T>> mergingIterables, OutputStream mergedStream) throws IOException {
        var iteratorPriorityQueue = new PriorityQueue<PeekingIterator<T>>(
                (x, y) -> elementComparator.compare(x.peek(), y.peek())
        );
        for(var merging: mergingIterables) {
            var iterator = new PeekingIterator<>(merging);
            if (iterator.hasNext()) {
                iteratorPriorityQueue.add(iterator);
            }
        }
        while(!iteratorPriorityQueue.isEmpty()) {
            var iterator = iteratorPriorityQueue.poll();
            try {
                mergedStream.write((iterator.next() +"\n").getBytes(StandardCharsets.UTF_8));
            }catch (NoSuchElementException ex){
                Logger.getInstance().logWarning(ex);
            }
            if (iterator.hasNext()) {
                iteratorPriorityQueue.add(iterator);
            }
        }
    }
    private final Comparator<T> elementComparator;
}
