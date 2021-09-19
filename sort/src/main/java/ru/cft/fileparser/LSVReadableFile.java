package ru.cft.fileparser;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Function;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * @param <E> - element type
 * Iterable wrapper over line separated value file
 */
public class LSVReadableFile<E extends Serializable> implements Iterable<E> {
    /**
     * @see LineParsingIterator<E>
     */
    public LSVReadableFile(InputStream stream, Function<String, E> lineParser){
        this.stream = stream;
        this.lineParser = lineParser;
    }
    public Iterator<E> iterator() {
        return new LineParsingIterator<>(stream,lineParser);
    }
    private final InputStream stream;
    private final Function<String, E> lineParser;
}
