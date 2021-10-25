package com.models.APIModels;

/**
 * One of many classes, exclusively used by gson to parse from a string into a
 * java class for use internally. This one represents a score from the api
 */
public class JSONScore {
    public String usr;
    public Integer score;
    public Integer num_stars;
    public Integer usr_id;
}
