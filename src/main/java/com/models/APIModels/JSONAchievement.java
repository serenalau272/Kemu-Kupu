package com.models.APIModels;

/**
 * One of many classes, exclusively used by gson to parse from a string into a
 * java class for use internally. This one is designed to be constructed to
 * represent an achievement from json.
 */
public class JSONAchievement {
    public String name; // Internal name representation
    public String display_name; // shown to user
    public String description;
}
