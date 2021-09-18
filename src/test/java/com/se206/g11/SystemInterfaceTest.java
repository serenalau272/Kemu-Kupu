package com.se206.g11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class SystemInterfaceTest {
    static final String p = "./testingTemporaryFiles";
    static final int numFiles = 5;

    private static void createFile(String path, String[] content) throws IOException {
        File f = new File(path);
        f.createNewFile();
        FileWriter w = new FileWriter(f);
        for (String l: content) {
            w.write(l);
        }
        w.close();
    }

    @BeforeAll
    public static void before() throws IOException {
        //create some files and folders for testing purposes
        File f = new File(p);
        f.mkdir();

        for (int i = 0; i < numFiles; i++) {
            String[] s = new String[]{"hello" + i + ",world" + i + "\nwild" + i + ",things" + i};
            String n = p + "/testFile" + i;
            createFile(n, s);
        }
        String[] s = {"hello,world"};
        createFile(p + "/singleFile", s);
    }

    @Test
    public void testGetTopics() throws IOException {
        //Check that we read all file names correctly
        List<SpellingTopic> t = SystemInterface.getTopics(p);
        List<String> f = Arrays.asList(new File(p).list());
        List<String> s = new ArrayList<String>();
        t.forEach(x -> s.add(x.getName()));
        s.removeAll(f);
        assertEquals(s.size(), 0);

        //Check that the file path is correct for all
        t.forEach(x -> assertEquals(p + "/" + x.getName(), x.getPath()));
    }

    @Test
    public void testGetWords() throws IOException {
        SpellingTopic t = new SpellingTopic("testFile2", p + "/testFile2");    
        List<Word> w1 = SystemInterface.getWords(2, t.getPath());
        List<Word> w2 = SystemInterface.getWords(1, t.getPath());
        List<Word> w3 = SystemInterface.getWords(100, t.getPath());
        List<Word> w4 = SystemInterface.getWords(100, new SpellingTopic("singleFile", p + "/singleFile").getPath());
        assertEquals(2, w1.size());
        assertEquals(1, w2.size());
        assertEquals(2, w3.size());

        Word w = w4.get(0);
        assertEquals("hello", w.getMaori());
        assertEquals("world", w.getEnglish());
    }

    @AfterAll
    public static void after() throws IOException {
        File f = new File(p);
        Arrays.asList(f.list()).forEach(x -> new File(p + "/" + x).delete());
        f.delete();
    }
}
