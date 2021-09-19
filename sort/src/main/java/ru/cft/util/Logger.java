package ru.cft.util;
import java.io.PrintStream;

/**
 * @author Daniel Kabzhan
 * @version 1.0
 * @since 2021-09-16
 * Simple logger class for warnings and errors
 */
public class Logger {
    public static Logger getInstance() {
        if(instance==null) {
            instance = new Logger();
        }
        return instance;
    }
    public void logWarning(Exception ex){
        if(LOGS_ON) {
            printer.println("WARNING: " + ex.getMessage());
        }
    }
    public void logInputError(Exception ex){
        if(LOGS_ON) {
            printer.println("ERROR! " + ex.getMessage() + "\nTry again.");
        }
    }
    public void logFatalError(Exception ex){
        if(LOGS_ON){
            printer.println("FATAL ERROR! "+ ex.getMessage());
        }
    }
    private static Logger instance = null;
    private final boolean LOGS_ON = true;
    private final PrintStream printer = System.out;
}
