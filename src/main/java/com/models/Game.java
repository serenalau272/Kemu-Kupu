package com.models;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.List;

import com.enums.Gamemode;
import com.enums.Status;
import com.util.SystemIO;

import javafx.scene.Node;

/**
 * This class handles the storing and retriving of the current gamestate.
 */
public class Game {
    private Gamemode gamemode;
    private List<Word> words;
    private SpellingTopic topic;
    private int wordIndex = 0;
    public Node inputField;

    /**
     * Create a new game instance
     * @param mode the gamemode we are testing in
     * @param topic the topic to select
     * @param numWords optional param for the number of words, defaults to 5
     */

    public Game(Gamemode mode) throws IOException {
        this.gamemode = mode;
    }

    /**
     * Get a word from the wordlist
     * @return
     */
    public Word getWord() {
        return this.words.get(wordIndex);
    }

    public List<Word> getWords() {
        return this.words;
    }

    public Gamemode getGameMode() {
        return this.gamemode;
    }

    public int getWordIndex(){
        return wordIndex;
    }

    public void setWordIndex(int index){
        wordIndex = index;
    }

    
    /**
     * Get the number of words in the word list
     * @return
     */
    public int getWordListSize() {
        return this.words.size();
    }

    /**
     * Get the overall score of the words
     * @return
     */
    public int getScore() {
        int score = 0;
        for (Word word : this.words)         {
            Status status = word.getStatus();
            if (status == Status.MASTERED || status == Status.FAULTED) {
                score += word.getScoreMultiplier() * 5;
            }
        }
        return score;
    }

    /**
     * Get the topic we are currently testing on
     * @return
     */
    public SpellingTopic getTopic() {
        return this.topic;
    }

    public void setTopic(SpellingTopic topic) throws IOException {
        this.topic = topic;
        this.words = SystemIO.getWords(5, topic.getPath());
    }

    public static Integer getHighScore() throws IOException {
        File userStats = new File("./.user/.userStats.txt");
        Scanner statsReader = new Scanner(userStats);
        Integer highScore = 0;
        if (statsReader.hasNextLine()) {
            highScore = statsReader.nextInt();
        }
        statsReader.close();
        return highScore;
    }
}
