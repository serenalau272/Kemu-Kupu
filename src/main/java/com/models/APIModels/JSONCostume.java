package com.models.APIModels;

/**
 * One of many classes, exclusively used by gson to parse from a string into a
 * java class for use internally. This one is designed to be constructed to
 * represent a costume from json.
 */
public class JSONCostume {
    public String name; // Internal name representation
    public String display_name; // shown to user
    public String description;
    public Integer price;
}
