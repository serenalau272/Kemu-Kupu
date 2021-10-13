package com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.models.SpellingTopic;

import org.junit.jupiter.api.Test;

public class SpellingTopicTest {
    @Test
    public void testNameTitleCase() {
        SpellingTopic t = new SpellingTopic("helloWorldMyGuy", null);
        assertEquals("Hello World My Guy", t.getNameTitleCase());

        t = new SpellingTopic("alllowercasemydude", null);
        assertEquals("Alllowercasemydude", t.getNameTitleCase());

        t = new SpellingTopic("ALLUPPERCASE", null);
        assertEquals("ALLUPPERCASE", t.getNameTitleCase());

        t = new SpellingTopic("1234", null);
        assertEquals("1234", t.getNameTitleCase());
    }

    @Test
    public void testPath() {
        SpellingTopic t = new SpellingTopic(null, "world");
        assertEquals("world", t.getPath());
    }
}
