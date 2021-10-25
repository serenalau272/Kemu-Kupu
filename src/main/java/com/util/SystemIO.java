package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.models.SpellingTopic;
import com.models.Word;

/*
    Handles I/O with the underlying system
*/
public class SystemIO {
    // Location of word files on the system.
    private static final String wordDir = "./words";

    //// Private Methods (helper functions) ////

    /**
     * A simple function to parse csv. WARNING: LIMITED! - Unable to parse lines
     * that contain `"` - they will be stripped. - Unable to parse csv that goes
     * across multi lines - Can only parse csv with 2 columnns
     * 
     * @param l the line to parse
     * @return a parsed word
     * @throws IOException
     */
    private static Word __parse_csv_line(String l) throws IOException {
        String curr = "";
        Boolean quoted = false;
        for (int i = 0; i < l.length(); i++) {
            if (l.charAt(i) == '\"')
                quoted = !quoted;
            else if (l.charAt(i) == ',' && !quoted)
                return new Word(curr, l.substring(i + 1).replaceAll("\"", ""));
            else
                curr += l.charAt(i);
        }
        throw new IOException("Unable to parse string to csv: " + l);
    }

    //// Public Methods ////

    /**
     * Opens a named file, and returns a specified number of randomised words from
     * that file. It is assumed that each word is on it's own line. Will work if the
     * file has no words, but will return an empty list.
     * 
     * Note that this function relies on the third party package OpenCSV:
     * http://opencsv.sourceforge.net/
     * 
     * @param numWords number of words to read from file.
     * @param path     path to the file to read wors from.
     * @return a list of word
     * @throws IOException
     * 
     *                     If the file does not have enough words to meet the number
     *                     of words requested, it will return all words available in
     *                     a randomised order.
     */
    public static List<Word> getWords(int numWords, String path) throws IOException {
        // Read and parse csv
        List<Word> w = new ArrayList<Word>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for (String l; (l = br.readLine()) != null;) {
            w.add(__parse_csv_line(l));
        }
        br.close();

        // Generate random lines
        Random r = new Random();
        List<Word> res = new ArrayList<Word>();
        for (int i = 0; i < numWords; i++) {
            if (w.size() == 0)
                return res; // We are out of words to add!
            res.add(w.remove(r.nextInt(w.size())));
        }
        return res;
    }

    /**
     * Returns a list of available word topics to choose from. If no topics are
     * available, returns an empty list.
     * 
     * @return List of spelling topics.
     * @throws IOException
     */
    public static List<SpellingTopic> getTopics() throws IOException {
        return getTopics(wordDir);
    }

    /**
     * Returns a list of available word topics to choose from. If no topics are
     * available, returns an empty list.
     * 
     * @return List of spelling topics.
     * @throws IOException
     */
    public static List<SpellingTopic> getTopics(String path) throws IOException {
        List<SpellingTopic> result = new ArrayList<SpellingTopic>();
        File dir = new File(path);
        for (String name : dir.list()) {
            SpellingTopic topic = new SpellingTopic(name, path + "/" + name);
            try {
                if (getWords(1, topic.getPath()).size() == 0)
                    continue; // Not enough words
            } catch (Exception e) {
            }
            result.add(topic);
        }
        return result;
    }
}
