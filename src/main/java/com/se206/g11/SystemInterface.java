package com.se206.g11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.se206.g11.models.Language;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.models.Word;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
    private static final String wordDir = "./words";

    //// Private Methods (helper functions) ////

    /**
     * Internal function to read word a word to the user using festival
     * @param word the word to read
     * @param repeats the number of times to repeat the word
     * @param language the language to which words are read out
     */
    //TODO - return a handle which can be modified by an API to end execution early.
    // See: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/doc-files/threadPrimitiveDeprecation.html
    private static void __readWord(String word, int repeats, Language language) {
        //This thread prevents any lockup of the front end.
        String speedCommand = "\"(Parameter.set 'Duration_Stretch "+  MainApp.getSettings().getSpeedFactor() + ")\"";
        String langCommand = (language == Language.MAORI) ? "voice_akl_mi_pk06_cg" : "voice_akl_nz_cw_cg_cg" ;        
        String wordCommand = "\"(SayText \\\"" + word +"\\\")\"";
        Thread t = new Thread(() -> {
            try {
                for (int i = 0; i < Integer.max(repeats, 1); i++) {
                    ProcessBuilder c = new ProcessBuilder("/bin/bash", "-c", "echo \"(" + langCommand + ")\" " + speedCommand + " " + wordCommand + " | festival");  
                    Process p = c.start();
                    p.waitFor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    /**
     * A simple function to parse csv.
     * WARNING: LIMITED!
     * - Unable to parse lines that contain `"` - they will be stripped.
     * - Unable to parse csv that goes across multi lines
     * - Can only parse csv with 2 columnns
     * @param l the line to parse
     * @return a parsed word
     * @throws IOException
     */
    private static Word __parse_csv_line(String l) throws IOException {
        String curr = "";
        Boolean quoted = false;
        for (int i = 0; i < l.length(); i++) {
            if (l.charAt(i) == '\"') quoted = !quoted;
            else if (l.charAt(i) == ',' && !quoted) return new Word(curr, l.substring(i+1).replaceAll("\"", ""));
            else curr += l.charAt(i);
        }
        throw new IOException("Unable to parse string to csv: " + l);
    }

    //// Public Methods ////    

    /** 
    *    Reads a word to a user utilising festival
    *    @param word The word or phrase to be read to the user
    */
    public static void readWord(String word) {
        __readWord(word, 1, Language.ENGLISH);
    }

    /** 
    *    Reads a word to a user utilising festival
    *    @param word The word or phrase to be read to the user
    *    @param i The language to which words are read out using
    */
    public static void readWord(String word, Language language) {
        __readWord(word, 1, language);
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
        __readWord(word, repeats, Language.MAORI);
    }

    /**
     * Opens a named file, and returns a specified number of randomised words from that file.
     * It is assumed that each word is on it's own line.
     * Will work if the file has no words, but will return an empty list.
     * 
     * Note that this function relies on the third party package OpenCSV: http://opencsv.sourceforge.net/
     * 
     * @param numWords number of words to read from file.
     * @param path path to the file to read wors from.
     * @return a list of word
     * @throws IOException
     * 
     * If the file does not have enough words to meet the number of words requested, it will
     * return all words available in a randomised order.
    */
    public static List<Word> getWords(int numWords, String path) throws IOException {
        //Read and parse csv
        List<Word> w = new ArrayList<Word>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for(String l; (l = br.readLine()) != null;)  {
            w.add(__parse_csv_line(l));
        }
        br.close();
        //Generate random lines
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

    /**
     * Play a sound from the resources/sound folder.
     * SAFTEY: As this is a built-in javafx method execution is ended automatically upon termination of the main thread.
     * WARNING: Will fail if an unknown sound is provided (full thread crash). //TODO add file validation
     * @param sound the name of the sound to play
     */
    public static void play_sound(String sound) {
        if (!MainApp.getSettings().getMusic()) return;

        try {
            String path = MainApp.class.getResource("/sound/" + sound + ".wav").toURI().toString();
            //Play sound
            new MediaPlayer(new Media(path)).play();
        } catch (URISyntaxException exception){
            System.err.println("Unable to load sound file: " + sound);
        }
    }
}
