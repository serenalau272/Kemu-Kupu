package com.models;

import java.io.IOException;
import java.util.List;

import com.enums.Gamemode;
import com.enums.Status;
import com.util.SystemIO;

import javafx.scene.Node;

/**
 * This class handles the storing and retriving of the current gamestate.
 */
public class Game {
    //// Properties ////
    private Gamemode gamemode;
    private List<Word> words;
    private SpellingTopic topic;
    private int wordIndex = 0;
    private boolean awaitingInput = true;
    public Node inputField;

    //// Public Methods /////
    /**
     * Create a new game instance
     * 
     * @param mode     the gamemode we are testing in
     * @param topic    the topic to select
     * @param numWords optional param for the number of words, defaults to 5
     */
    public Game(Gamemode mode) throws IOException {
        this.gamemode = mode;
    }

    /**
     * Get a word from the wordlist
     * 
     * @return
     */
    public Word getWord() {
        return this.words.get(wordIndex);
    }

    /**
     * Get the number of words in the word list
     * 
     * @return an integer representing the number of words
     */
    public int getWordListSize() {
        return this.words.size();
    }

    /**
     * Calculate and return the current total score.
     * 
     * @return an integer representing the current total score
     */
    public int getScore() {
        int score = 0;
        for (Word word : this.words) {
            Status status = word.getStatus();
            if (status == Status.MASTERED || status == Status.FAULTED) {
                score += word.getScoreMultiplier() * 5;
            }
        }
        return score;
    }

    /**
     * Set the current topic we are going to play
     * 
     * @param topic the topic selection
     * @throws IOException if unable to read the words from disk
     */
    public void setTopic(SpellingTopic topic) throws IOException {
        this.topic = topic;
        this.words = SystemIO.getWords(5, topic.getPath());
    }

    /**
     * Get the current spelling topic for this game.
     * @return the current topic
     */
    public SpellingTopic getTopic() {
        return this.topic;
    }

    /**
     * Get the list of all words that the user has been tested on.
     * This should only be used for display purposes, not modification.
     * @return a list of words
     */
    public List<Word> getWords() {
        return this.words;
    }

    /**
     * Get the current gamemode for the quiz.
     * @return
     */
    public Gamemode getGameMode() {
        return this.gamemode;
    }

    /**
     * Get the current word we are up to, should be used for display purposes.
     * @return an integer representing the current word in the wordlist
     */
    public int getWordIndex() {
        return this.wordIndex;
    }

    /**
     * Set the current word we are up to.
     * Note that this doesn't have any validation in terms of being in-bounds.
     * @param index
     */
    public void setWordIndex(int index) {
        this.wordIndex = index;
    }

    /**
     * Set whether we are awaiting input
     * @param value the boolean to save
     */
    public void setAwaitingInput(boolean value) {
        this.awaitingInput = value;
    }

    /**
     * Check whether we are awaiting input or not
     * @return a boolean representing whether or not we are waiting for input.
     */
    public boolean getAwaitingInput() {
        return this.awaitingInput;
    }
}
