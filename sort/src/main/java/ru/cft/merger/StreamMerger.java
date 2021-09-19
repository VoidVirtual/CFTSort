package ru.cft.merger;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import ru.cft.fileparser.LSVReadableFile;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * @param <T> - merging elements' type
 */
public class StreamMerger<T extends Serializable&Comparable<T>> extends Merger<T> implements FileMerger {
    /**
     * Class constructor.
     * @param parser - a functor, creating {@link T} type element from {@link String}.
     * @param comparator - a functor, comparing elements during merge.
     */
    public StreamMerger(Function<String,T> parser, Comparator<T> comparator){
        super(comparator);
        this.parser = parser;
    }
    public StreamMerger(Function<String,T> parser){
        this.parser = parser;
    }

    /**
     * Merges supposed to be sorted input streams into output stream.
     * If an input stream isn't sorted, the output stream will be sorted partial.
     *
     * @param mergingStreams - streams with supposed to be sorted elements, that will be merged.
     * @param mergedStream - stream where the merging elements will be written.
     * @throws IOException - if I/O error occurs
     */
    public void mergeStreams(List<? extends InputStream> mergingStreams, OutputStream mergedStream) throws IOException{
        var mergingFiles = new ArrayList<LSVReadableFile<T>>();
        for(var mergingStream: mergingStreams){
            mergingFiles.add(new LSVReadableFile<>(mergingStream,parser));
        }
        mergeIterables(mergingFiles,mergedStream);
    }
    /**
     *
     * @param order - natural or reverse order of sort. See {@link SortOrder}
     * @param type - type of the elements, that will be parsed from file. See {@link ElementType}
     * @return - see {@link FileMerger}
     */
    public static FileMerger create(SortOrder order, ElementType type){
        return switch (type) {
            case STRING -> createStringMerger(order);
            case INTEGER -> createIntMerger(order);
        };
    }
    public static StreamMerger<String> createStringMerger(SortOrder order){
        return switch (order) {
            case NATURAL -> new StreamMerger<>(Function.identity());
            case REVERSE -> new StreamMerger<>(Function.identity(), Comparator.reverseOrder());
        };
    }
    public static  StreamMerger<Integer> createIntMerger(SortOrder order){
        return switch (order) {
            case NATURAL -> new StreamMerger<>(Integer::parseInt);
            case REVERSE -> new StreamMerger<Integer>(Integer::parseInt, Comparator.reverseOrder());
        };
    }
    private final Function<String,T> parser;
}
