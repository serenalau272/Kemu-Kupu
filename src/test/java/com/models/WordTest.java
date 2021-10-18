package com.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordTest {
    @Test
    public void testGetters() {
        Word word = new Word("hello", "world");
        assertEquals(word.getMaori(), "hello");
        assertEquals(word.getEnglish(), "world");
    }

    // @Test
    // public void testIsEqualLazy() {
    //     Word word1 = new Word("hello", "world");
    //     Word word2 = new Word("héllo", null);
    //     Word word3 = new Word(null, "something");

    //     assertTrue(word1.isEqualLazy(word2));
    //     assertFalse(word1.isEqualLazy(word3));
    // }

    @Test
    public void testIsEqualStrict() {
        Word word1 = new Word("hello", "world");
        Word word2 = new Word("héllo", "wórld");
        Word word3 = new Word("other", "something");

        assertFalse(word1.isEqualStrict(word2));
        assertFalse(word1.isEqualStrict(word3));
        assertTrue(word1.isEqualStrict(word1));
    }
}
