package ru.cft;
import org.apache.commons.cli.*;
import ru.cft.merger.FileMerger.*;

import java.util.List;

/**
 *
 */
public class ApplicationParameters {
    public ApplicationParameters(){

    }
    /**
     * Class constructor, parses the command line arguments
     * @param args - command line arguments
     * @throws ParseException
     */
    public ApplicationParameters(String[] args) throws ParseException{

        var options = new Options();
        var elementTypeOptions = new OptionGroup();
        elementTypeOptions.addOption(Option.builder("s").build());
        elementTypeOptions.addOption(Option.builder("i").build());

        var sortOrderOptions = new OptionGroup();
        sortOrderOptions.addOption(Option.builder("a").build());
        sortOrderOptions.addOption(Option.builder("d").build());

        elementTypeOptions.setRequired(true);
        sortOrderOptions.setRequired(false);
        options.addOptionGroup(elementTypeOptions);
        options.addOptionGroup(sortOrderOptions);

        var parser = new DefaultParser();
        var cmd = parser.parse(options, args);
        elementType = cmd.hasOption("s") ? ElementType.STRING :
                cmd.hasOption("i") ? ElementType.INTEGER :
                        null;
        sortOrder = cmd.hasOption("d") ? SortOrder.REVERSE :
                SortOrder.NATURAL;

        var fileNames = cmd.getArgList();
        if (fileNames.size() < 2) {
            throw new ParseException("Not enough file names provided.");
        }
        outputFileName = fileNames.get(0);
        inputFileNames = fileNames.subList(1, fileNames.size());
    }
    public ApplicationParameters setSortOrder(SortOrder order){
        this.sortOrder = order;
        return this;
    }
    public ApplicationParameters setElementType(ElementType type){
        this.elementType = type;
        return this;
    }
    public ApplicationParameters setInputFileNames(List<String> fileNames){
        this.inputFileNames = fileNames;
        return this;
    }
    public ApplicationParameters setOutputFileName(String fileName){
        this.outputFileName = fileName;
        return this;
    }
    public SortOrder sortOrder;
    public ElementType elementType;
    public  String outputFileName;
    public  List<String> inputFileNames;
}
