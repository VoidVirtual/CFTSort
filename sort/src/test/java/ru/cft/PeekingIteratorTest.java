package ru.cft;

import org.junit.Assert;
import org.junit.Test;
import ru.cft.util.PeekingIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PeekingIteratorTest {
    @Test
    public void TestEmptyCollection(){
        var emptyList = new ArrayList<Integer>();
        var iterator = new PeekingIterator<>(emptyList);
        Assert.assertFalse(iterator.hasNext());
        try{
            iterator.next();
            Assert.fail();
        }catch (NoSuchElementException ex){
            Assert.assertTrue(true);
        }
    }
    @Test
    public void TestPeekingVSDefault(){
        var list = List.of(1,2,3);
        var it = list.iterator();
        var peekingIt = new PeekingIterator<>(list);
        int iterationCount = 0;
        while(it.hasNext() && peekingIt.hasNext()){
            Assert.assertEquals(it.next(), peekingIt.next());
            ++iterationCount;
        }
        Assert.assertEquals(3,iterationCount);
    }
    @Test
    public void TestPeekVSNext(){
        var list = List.of(1,2,3);
        var peekingIt = new PeekingIterator<>(list);
        while(peekingIt.hasNext()){
            var peek = peekingIt.peek();
            Assert.assertEquals(peek,peekingIt.next());
        }
    }
}
