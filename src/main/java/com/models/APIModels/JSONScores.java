package com.models.APIModels;

import java.util.List;

/**
 * One of many classes, exclusively used by gson to parse from a string into a java class for use internally.
 * This one represents a collection of scores from the api
 */
public class JSONScores {
    public List<JSONScore> data;    
}
