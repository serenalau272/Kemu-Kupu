package com.se206.g11;

/**
 * A topic that can be selected by the user for a quiz
 */
public class SpellingTopic {
    private String name;
    private String path;
    
    /**
     * Create a new spelling topic
     * @param name the name of the spelling topic, should be provided in camelCase format (same name as the file).
     * @param path the path to the file containing the words
     */
    SpellingTopic(String name, String path) {
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
     * @return Get the topic of this word list in title case
     */    
    public String getNameTitleCase() {
        String s1 = this.name.substring(0, 1).toUpperCase(); //capatalize first letter
        return s1 + this
            .name.substring(1)
            .replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2"); //convert from camel case
    }

    /**
     * @return Get the name of this word list - raw as from disk.
     */
    public String getName() {
        return this.name;
    }
}
