package ru.cft;
import org.junit.Assert;
import org.junit.Test;
import ru.cft.merger.FileMerger.*;
import ru.cft.merger.Merger;
import ru.cft.merger.StreamMerger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.Assert.fail;

public class StreamMergerTest {
    @Test
    public void NaturalOrderIntegerTest(){
        var fileMerger=
                StreamMerger.create(SortOrder.NATURAL, ElementType.INTEGER);
        var in1 = new ByteArrayInputStream(
                "1\n4\n9".getBytes(StandardCharsets.UTF_8));
        var in2 = new ByteArrayInputStream(
                "1\n8\n27".getBytes(StandardCharsets.UTF_8));
        var in3 = new ByteArrayInputStream(
                "1\n2\n3".getBytes(StandardCharsets.UTF_8));
        var ins = List.of(in1,in2,in3);
        var out = new ByteArrayOutputStream();
        try {
            fileMerger.mergeStreams(ins, out);
            var expected = "1\n1\n1\n2\n3\n4\n8\n9\n27\n";
            assertEquals(expected, out);
        }catch (IOException ex){
            fail();
        }
    }
    @Test
    public void ReverseOrderIntegerTest(){
        var fileMerger= StreamMerger.create(SortOrder.REVERSE, ElementType.INTEGER);
        var in1 = new ByteArrayInputStream(
                "9\n4\n1".getBytes(StandardCharsets.UTF_8));
        var in2 = new ByteArrayInputStream(
                "27\n8\n1".getBytes(StandardCharsets.UTF_8));
        var in3 = new ByteArrayInputStream(
                "3\n2\n1".getBytes(StandardCharsets.UTF_8));
        var ins = List.of(in1,in2,in3);
        var out = new ByteArrayOutputStream();
        try {
            fileMerger.mergeStreams(ins, out);
            var expected = "27\n9\n8\n4\n3\n2\n1\n1\n1\n";
            assertEquals(expected, out);
        }catch (IOException ex){
            fail();
        }
    }
    @Test
    public void NaturalOrderStringTest() {
        var fm = StreamMerger.create(SortOrder.NATURAL, ElementType.STRING);
        var in1 = new ByteArrayInputStream(("""
                AAAAA
                DDDDD
                IIIII
                """).getBytes(StandardCharsets.UTF_8) );
        var in2 = new ByteArrayInputStream(("""
                AAAAA
                HHHHH
                ZZZZZ
                """).getBytes(StandardCharsets.UTF_8) );
        var in3 = new ByteArrayInputStream(("""
                AAAAA
                BBBBB
                CCCCC
                """).
                getBytes(StandardCharsets.UTF_8) );
        var expected =  ("""
                AAAAA
                AAAAA
                AAAAA
                BBBBB
                CCCCC
                DDDDD
                HHHHH
                IIIII
                ZZZZZ
                """);
        var out = new ByteArrayOutputStream();
        try {
            fm.mergeStreams(List.of(in1, in2, in3), out);
            assertEquals(expected, out);
        }catch (IOException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void ReverseOrderStringTest(){
        var merger = StreamMerger.create(SortOrder.REVERSE, ElementType.STRING);
        var in1 = new ByteArrayInputStream(("""
                    IIIII
                    DDDDD
                    AAAAA
                    """).getBytes(StandardCharsets.UTF_8));
        var in2 = new ByteArrayInputStream(("""
                    ZZZZZ
                    HHHHH
                    AAAAA
                    """).getBytes(StandardCharsets.UTF_8));
        var in3 = new ByteArrayInputStream(("""
                    CCCCC
                    BBBBB
                    AAAAA
                    """).getBytes(StandardCharsets.UTF_8));
        var out = new ByteArrayOutputStream();
        var expected = ("""
                    ZZZZZ
                    IIIII
                    HHHHH
                    DDDDD
                    CCCCC
                    BBBBB
                    AAAAA
                    AAAAA
                    AAAAA
                    """);
        try {
            merger.mergeStreams(List.of(in1, in2,in3), out);
            assertEquals(expected, out);

        }catch (IOException ex){
            fail(ex.getMessage());
        }
    }
    @Test
    public void TestUnsorted(){
        var merger = new Merger<Integer>();
        var output = new ByteArrayOutputStream();
        var ins =  List.of(List.of(1,2,4),List.of(3,2,1));
        try {
            merger.mergeIterables(ins, output);
        }catch (IOException ex){
            fail();
        }
        assertEquals("1\n2\n3\n2\n1\n4\n", output);
    }
    public void assertEquals(String expectedStr, ByteArrayOutputStream actualStream){
        Assert.assertEquals(expectedStr,actualStream.toString(StandardCharsets.UTF_8));
    }
}
