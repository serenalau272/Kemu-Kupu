package com.se206.g11;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Random;
import com.opencsv.bean.CsvToBeanBuilder;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

/*
    This class is entirely made up of static functions, and is designed to handle all operations 
    that interface with the underlying OS.
    This includes all file i/o and bash commands to services such a festival for a tts implementation.
    
    Functions are designed to be forgiving, and generally throw errors to the user to handle instead
    of try/catching them internally (where reasonable).
*/
public class SystemInterface {
    //// Properties ////
    // Note that these can easily be migrated to a .env file or similar for configuration purposes.

    // Location of word files on the system.
    private static String wordDir = "./words";

    //// Private Methods (helper functions) ////

    /**
     * Internal function to read word a word to the user using festival
     * @param word the word to read
     * @param repeats the number of times to repeat the word
     */
    //TODO - Implement speaking speed adjustment
    //TODO - Implement language selection (use an enum, to select between Maori and English)
    //TODO - return a handle which can be modified by an API to end execution early.
    // See: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/doc-files/threadPrimitiveDeprecation.html
    private static void __readWord(String word, int repeats) {
        //This thread prevents any lockup of the front end.
        Thread t = new Thread(() -> {
            try {
                for (int i =0; i< repeats; i++) {
                    ProcessBuilder c = new ProcessBuilder("/bin/bash", "-c", "echo \"" +  word + "\" | festival --tts");
                    Process p = c.start();
                    p.waitFor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();
    }

    //// Public Methods ////    

    /** 
    *    Reads a word to a user utilising festival
    *    @param word The word or phrase to be read to the user
    */
    public static void readWord(String word) {
        __readWord(word, 0);
    }

    /**
        Takes a word and a number, and reads the word to the user utilising Festival.
        Does not check whether this was succesful.
        Will repeat the word to the user the specified number of times.
        @param word The word to be read to the user
        @param repeats The number of times the word should be repeated to the user (note that very 
        high values will repeat for a very long time, as this is asynchronously threaded).
    */
    public static void readWord(String word, int repeats) {
        __readWord(word, repeats);
    }

    /**
     * Opens a named file, and returns a specified number of randomised words from that file.
     * It is assumed that each word is on it's own line.
     * Will work if the file has no words, but will return an empty list.
     * 
     * Note that this function relies on the third party package OpenCSV: http://opencsv.sourceforge.net/
     * 
     * @param numWords number of words to read from file.
     * @param filePath path to the file to read wors from.
     * @return a list of words
     * @throws IOException
     * 
     * If the file does not have enough words to meet the number of words requested, it will
     * return all words available in a randomised order.
    */
    public static List<Word> getWords(int numWords, String filePath) throws IOException {
        Path file = Paths.get(filePath);
        if (!Files.exists(file)) throw new IOException("File does not exist!");
        List<Word> w = new CsvToBeanBuilder<Word>(new FileReader(filePath))
            .withType(Word.class)
            .build()
            .parse();
        Random r = new Random();
        List<Word> res = new ArrayList<Word>();
        for(int i=0; i < numWords; i++) {
            if (w.size() == 0) return res; //We are out of words to add!
            res.add(w.remove(r.nextInt(w.size())));
        }
        return res;
    }

    /**
     * Returns a list of available word topics to choose from. If no topics are available, returns an empty list.
     * @return List of spelling topics.
     * @throws IOException
     */
    public static List<SpellingTopic> getTopics() throws IOException {
        return getTopics(wordDir);
    }

    /**
     * Returns a list of available word topics to choose from. If no topics are available, returns an empty list.
     * @return List of spelling topics.
     * @throws IOException
     */
    //TODO validate that a topic actually has at least 1 word to test on with.
    public static List<SpellingTopic> getTopics(String path) throws IOException {
        List<SpellingTopic> result = new ArrayList<SpellingTopic>();
        File dir = new File(path);
        for (String name : dir.list()) {
            result.add(new SpellingTopic(name, path + "/" + name));
        }
        return result;
    }
}
