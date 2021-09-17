package com.se206.g11;

import com.opencsv.bean.CsvBindByPosition;

/**
 * A word the user may be tested on. Has english and maori representations stored internally.
 */
public class Word {
    @CsvBindByPosition(position = 0)
    private String maori;
    @CsvBindByPosition(position = 1)
    private String english;

    /**
     * Create a new word, with the english and maori translations
     * Note that translations will be automatically stripped and lowercased for conversion purposes.
     * @param english
     * @param maori
     */
    Word(String maori, String english) {
        this.english = english.strip().toLowerCase();
        this.maori = maori.strip().toLowerCase();
    }

    /**
     * Checks if two words are equal, ignoring accents. I.e. "é" == "e" will return true.
     * @param word the word to compare against.
     * @return true if equal, false otherwise.
     */
    public Boolean isEqualLazy(Word word) {
        //TODO 
        return false;
    }

    /**
     * Checks if two words are equal, strictly checking accents. I.e. "é" == "e" will return false.
     * @param word the word to compare against.
     * @return true if equal, false otherwise.
     */
    public Boolean isEqualStrict(Word word) {
        // TODO
        return false;
    }

    /** 
     * @return the english translation of the word.
     */
    public String english() {
        return this.english;
    }

    /**
     * @return the maori translation of the word.
     */
    public String maori() {
        return this.maori;
    }
}
