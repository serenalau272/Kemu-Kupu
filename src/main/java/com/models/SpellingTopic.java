package com.models;

/**
 * A topic that can be selected by the user for a quiz
 */
public class SpellingTopic {
    private String name;
    private String path;

    /**
     * Create a new spelling topic
     * 
     * @param name the name of the spelling topic, should be provided in camelCase
     *             format (same name as the file).
     * @param path the path to the file containing the words
     */
    public SpellingTopic(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * @return Get the path to this word list
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @return Get the name of this word list - raw as from disk.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Overriden equals method
     * 
     * @return whether two SpellingTopic are equal based on equivalent paths and
     *         names
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            SpellingTopic otherTopic = (SpellingTopic) obj;
            return this.getPath().equals(otherTopic.getPath()) && this.getName().equals(otherTopic.getName());
        }

        return false;
    }
}
