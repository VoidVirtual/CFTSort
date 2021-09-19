package ru.cft.fileparser;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * @param <E> - element type
 *
 */
public class LineParsingIterator<E> implements Iterator<E> {
    /**
     * Class constructor.
     * @param input - stream from which lines will be read
     * @param parser - function, producing E type object from a line
     */
    public LineParsingIterator(InputStream input, Function<String, E> parser) {
        scanner = new Scanner(input, StandardCharsets.UTF_8).useDelimiter("\n");
        this.lineParser = parser;
    }
    @Override
    public boolean hasNext(){
        return scanner.hasNext();
    }
    @Override
    public E next() throws NoSuchElementException {
        try {
            return lineParser.apply(scanner.next());
        }catch (Exception ex){
            throw new NoSuchElementException(ex);
        }
    }
    private final Function<String,E> lineParser;
    private final Scanner scanner;
}
