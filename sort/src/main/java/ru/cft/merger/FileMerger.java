package ru.cft.merger;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public interface FileMerger {
    /**
     * Merges supposed to be sorted input streams into output stream.
     * If an input stream isn't sorted, the output stream will be sorted partial.
     * @param mergingStreams - streams with supposed to be sorted elements, that will be merged.
     * @param mergedStream - stream where the merging elements will be written.
     * @throws IOException - if I/O error occurs
     */
    void mergeStreams(List<? extends InputStream> mergingStreams, OutputStream mergedStream) throws IOException;
    /**
     *
     * @param mergingFilePaths - the path to the files that will be merged
     * @param mergedFilePath - the path to the file where elements will be merged
     * @throws IOException - if an I/O error occurs
     * @throws IllegalArgumentException - if a merged filepath matches with any merging filepath
     */
    default void mergeFiles(List<String> mergingFilePaths, String mergedFilePath) throws IOException,
                                                                                IllegalArgumentException{
        if(mergingFilePaths.contains(mergedFilePath)){
            throw new IllegalArgumentException("Merging file: "+
                                                mergedFilePath+
                                                " cannot be merged file at the same time.");
        }
        var outputStream = new FileOutputStream(mergedFilePath);
        var inputStreams = new ArrayList<FileInputStream>();
        for(var filepath: mergingFilePaths) {
            inputStreams.add(new FileInputStream(filepath));
        }
        mergeStreams(inputStreams,outputStream);
    }
    enum SortOrder{
        NATURAL,
        REVERSE
    }
    enum ElementType{
        STRING,
        INTEGER
    }
}
