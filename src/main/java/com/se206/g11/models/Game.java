package com.se206.g11.models;

import java.io.IOException;
import java.util.List;

import com.se206.g11.components.Clock;
import com.se206.g11.util.SystemIO;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * This class handles the storing and retriving of the current gamestate.
 */
public class Game {
    private Gamemode gamemode;
    private List<Word> words;
    private SpellingTopic topic;
    private int wordIndex = 0;
    private Clock timer;
    public Node inputField;

    /**
     * Create a new game instance
     * @param mode the gamemode we are testing in
     * @param topic the topic to select
     * @param numWords optional param for the number of words, defaults to 5
     */
    public Game(Gamemode mode, SpellingTopic topic, Integer numWords) throws IOException {
        this.gamemode = mode;
        this.topic = topic;
        this.words = SystemIO.getWords(numWords, topic.getPath());
    }

    public Game(Gamemode mode, SpellingTopic topic) throws IOException {
        this(mode, topic, 5);
    }

    /**
     * Get a word from the wordlist
     * @return
     */
    public Word getWord() {
        return this.words.get(wordIndex);
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

    public Clock getClock() {
        return this.timer;
    }

    public void setClock(Clock timer) {
        this.timer = timer;
    }
}
