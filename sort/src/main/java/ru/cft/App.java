package ru.cft;
import org.apache.commons.cli.ParseException;
import ru.cft.merger.*;
import ru.cft.util.Logger;
import java.io.IOException;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * Main class
 */
public class App{

    public static void main(String[] args) {
        try {
            var params = new ApplicationParameters(args);
            var merger = StreamMerger.create(params.sortOrder, params.elementType);
            merger.mergeFiles(params.inputFileNames,params.outputFileName);
        }catch (ParseException | IllegalArgumentException | IOException ex){
            Logger.getInstance().logInputError(ex);
        }catch (Exception ex){
            Logger.getInstance().logFatalError(ex);
        }
    }
}

