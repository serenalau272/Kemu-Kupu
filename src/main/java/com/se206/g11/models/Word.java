package com.se206.g11.models;

import com.se206.g11.enums.Status;

/**
 * A word the user may be tested on. Has english and maori representations stored internally.
 */
public class Word {
    private String maori;
    private String english;
    private Status status;
    private int time;
    private Word response;
    //// Constructors ////

    /**
     * Create an empty Word class
     */
    public Word() {
        this.english = null;
        this.maori = null;
        this.status = Status.SKIPPED;
        this.time = 0;
    }

    /**
     * Create a new word, with the english and maori translations
     * Note that translations will be automatically stripped and lowercased for conversion purposes.
     * @param english
     * @param maori
     */
    public Word(String maori, String english) {
        if (english != null) this.english = english.strip().toLowerCase();
        if (maori != null) this.maori = maori.strip().toLowerCase();
        this.status = Status.SKIPPED;
        this.time = 0;
    }

    //// Public Methods ////

    public Word getResponse(){
        return this.response;
    }


    /**
     * Checks if two words are equal, strictly checking accents. I.e. "é" == "e" will return false.
     * If one provides a word with the english or maori set to null, then only this will be
     * compared against the original. Will not compare both if both are set.
     * @param word the word to compare against.
     * @return true if equal, false otherwise.
     */
    public Boolean isEqualStrict(Word word) throws NullPointerException {
        if (word.getEnglish() == null && word.getMaori() == null) throw new NullPointerException("both english and maori are null");
        if (this.getEnglish() == null && this.getMaori() == null) throw new NullPointerException("both english and maori are null");

        this.response = word;
        //Carry out comparison
        boolean comparison = false;
        if (word.getEnglish() != null) comparison = word.getEnglish().equals(this.getEnglish());
        if (word.getMaori() != null) comparison =  word.getMaori().equals(this.getMaori());

        // Update the status
        if (comparison) {
            //Correct!
            switch (this.status) {
                case NOT_TESTED:
                    this.status = Status.MASTERED;
                    break;
                case SKIPPED:
                    this.status = Status.MASTERED;
                    break;
                case FAULTED:
                    this.status = Status.MASTERED;
                    break;
                default:
                    break;
            }
        } else {
            //Incorrect
            switch (this.status) {
                case NOT_TESTED:
                    this.status = Status.FAULTED;
                    break;
                case FAULTED:
                    this.status = Status.FAILED;
                    break;
                case SKIPPED: 
                    this.status = Status.FAULTED;
                    break;
                default:
                    break;
            }
        }
        
        return comparison;
    }

    /** 
     * @return the english translation of the word.
     */
    public String getEnglish() {
        return this.english;
    }

    /**
     * @return the maori translation of the word.
     */
    public String getMaori() {
        return this.maori;
    }

    /**
     * Set the english word translation for this word
     * @param s the word to set
     */
    public void setEnglish(String s) {
        this.english = s.strip().toLowerCase();
    }

    /**
     * Set the maori word translation for this word
     * @param s the word to set
     */
    public void setMaori(String s) {
        this.maori = s.strip().toLowerCase();
    }

    /**
     * Get the spelling status of this word.
     * @return
     */
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status s){
        this.status = s;
    }

    /**
     * Get the time the student took to answer this question!
     * @return
     */
    public int getScoreMultiplier() {
        return this.time;
    }

    /**
     * Set the time the student took to answer this question correctly!
     * @param time
     */
    public void setScoreMultiplier(int time) {
        this.time = time;
    }
}
