package com.se206.g11.models;

import java.text.Collator;
import com.opencsv.bean.CsvBindByPosition;

/**
 * A word the user may be tested on. Has english and maori representations stored internally.
 */
public class Word {
    @CsvBindByPosition(position = 0)
    private String maori;
    @CsvBindByPosition(position = 1)
    private String english;

    //// Constructors ////

    /**
     * Create an empty Word class
     */
    public Word() {
        this.english = "";
        this.maori = "";
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
    }

    //// Public Methods ////

    /**
     * Checks if two words are equal, ignoring accents. I.e. "é" == "e" will return true.
     * If one provides a word with the english or maori set to null, then only this will be
     * compared against the original. Will not compare both if both are set.
     * @param word the word to compare against.
     * @return true if equal, false otherwise.
     */
    public Boolean isEqualLazy(Word word) {
        final Collator i = Collator.getInstance();
        i.setStrength(Collator.NO_DECOMPOSITION);
        if (word.getEnglish() != null) return i.compare(word.getEnglish(), this.getEnglish()) == 0;
        if (word.getMaori() != null) return i.compare(word.getMaori(), this.getMaori()) == 0;
        return false;
    }

    /**
     * Checks if two words are equal, strictly checking accents. I.e. "é" == "e" will return false.
     * If one provides a word with the english or maori set to null, then only this will be
     * compared against the original. Will not compare both if both are set.
     * @param word the word to compare against.
     * @return true if equal, false otherwise.
     */
    public Boolean isEqualStrict(Word word) {
        if (word.getEnglish() != null) return word.getEnglish().equals(this.getEnglish());
        if (word.getMaori() != null) return word.getMaori().equals(this.getMaori());
        return false;
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
     * Set the english word to a provided String.
     * NOTE: Unlike the constructor, does not strip and lowercase the string.
     * @param s the english word to set
     */
    public void setEnglish(String s) {
        this.english = s;
    }

    /**
     * Set the maori word to a provided String.
     * NOTE: Unlike the constructor, does not strip and lowercase the string.
     * @param s the maori word to set
     */
    public void setMaori(String s) {
        this.maori = s;
    }
}
