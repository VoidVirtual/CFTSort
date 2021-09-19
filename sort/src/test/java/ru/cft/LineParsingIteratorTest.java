package ru.cft;

import org.junit.Assert;
import org.junit.Test;
import ru.cft.fileparser.LineParsingIterator;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class LineParsingIteratorTest {
    @Test
    public void TestParsingError(){
        var inputStream = new ByteArrayInputStream(
                """
                       1
                       nonsense
                       3""".getBytes(StandardCharsets.UTF_8)
        );
        var iterator = new LineParsingIterator<>(inputStream, Integer::parseInt);
        try {
            while (iterator.hasNext()) {
                iterator.next();
            }
            Assert.fail();
        }catch (NoSuchElementException ex){
            Assert.assertTrue(true);
        }
    }
}
