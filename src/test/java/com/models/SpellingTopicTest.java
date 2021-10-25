package com.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SpellingTopicTest {
    @Test
    public void testPath() {
        SpellingTopic t = new SpellingTopic(null, "world");
        assertEquals("world", t.getPath());
    }
}
