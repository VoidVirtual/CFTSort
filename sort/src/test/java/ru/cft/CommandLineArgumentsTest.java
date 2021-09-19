package ru.cft;

import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.cft.merger.FileMerger.*;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CommandLineArgumentsTest {
    @Test
    public void TestSortOrderOptionFirst(){
        String[] args = {"-d","-s","out.txt","in1.txt","in2.txt"};
        try {
            var actual = new ApplicationParameters(args);
            assertEquals(REVERSE_STRING_THREE_FILES, actual);
        }catch (ParseException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void ElementTypeOptionFirstTest(){
        String[] args = {"-s","-d","out.txt","in1.txt","in2.txt"};
        try {
            var actual = new ApplicationParameters(args);
            assertEquals(REVERSE_STRING_THREE_FILES, actual);
        }catch (ParseException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void TestFilesFirst(){
        String[] args = {"out.txt","in1.txt","in2.txt","-d","-s"};
        try {
            var actual = new ApplicationParameters(args);
            assertEquals(REVERSE_STRING_THREE_FILES, actual);
        }catch (ParseException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void TestSortOrderNotSpecified(){
        String[] args = {"-s","out.txt","in1.txt","in2.txt"};
        try {
            var actual = new ApplicationParameters(args);
            assertEquals(NATURAL_STRING_THREE_FILES,actual);
        }catch (ParseException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void TestNotEnoughFileNames(){
        String[] args = {"-s","-d", "out.txt"};
        try{
            new ApplicationParameters(args);
            fail("Not enough file names not handled");
        }catch(ParseException ex){
            assertTrue(true);
        }
    }
    @Test
    public void TestExtraOptions(){
        String[] args = {"-s","-d", "-b", "out.txt", "in.txt"};
        try{
            new ApplicationParameters(args);
            fail("Extra options not handled");
        }catch(ParseException ex){
            assertTrue(true);
        }
    }
    public static void assertEquals(ApplicationParameters expected, ApplicationParameters actual){
        Assert.assertEquals(expected.elementType, actual.elementType);
        Assert.assertEquals(expected.sortOrder, actual.sortOrder);
        Assert.assertEquals(expected.inputFileNames, actual.inputFileNames);
        Assert.assertEquals(expected.outputFileName, actual.outputFileName);
    }
    final ApplicationParameters REVERSE_STRING_THREE_FILES = new ApplicationParameters().
                                                    setOutputFileName("out.txt").
                                                    setInputFileNames(List.of("in1.txt","in2.txt")).
                                                    setElementType(ElementType.STRING).
                                                    setSortOrder(SortOrder.REVERSE);
    final ApplicationParameters NATURAL_STRING_THREE_FILES = new ApplicationParameters().
            setOutputFileName("out.txt").
            setInputFileNames(List.of("in1.txt","in2.txt")).
            setElementType(ElementType.STRING).
            setSortOrder(SortOrder.NATURAL);
}