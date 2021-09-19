package ru.cft.util;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * A Wrapper over {@link Iterator<E>} with additional peek() method.
 * @param <E> - element type
 */
public class PeekingIterator<E> implements Iterator<E> {

    /**
     * @param iterable - any iterable object
     */
    public PeekingIterator(Iterable<E> iterable) {
        nonPeekingIterator = iterable.iterator();
        if(nonPeekingIterator.hasNext()){
            hasNext = true;
            next = nonPeekingIterator.next();
        }
    }
    /**
     * @return - see {@link java.util.Iterator<E>}
     */
    public boolean hasNext(){
        return hasNext;
    }

    /**
     * @return - see {@link java.util.Iterator<E>}
     */
    public E next() throws NoSuchElementException{
        if(!hasNext){
            throw new NoSuchElementException();
        }
        E res = next;
        if(nonPeekingIterator.hasNext()) {
            next = nonPeekingIterator.next();
        }else{
            hasNext = false;
        }
        return res;
    }

    /**
     * @return - the value that next() method will return
     * doesn't change the state of the iterator
     */
    public E peek(){
        return next;
    }
    private final Iterator<E> nonPeekingIterator;
    private E next = null;
    boolean hasNext = false;
}