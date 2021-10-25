package com.models.APIModels;

import java.util.List;

/**
 * One of many classes, exclusively used by gson to parse from a string into a
 * java class for use internally. This one is designed to be constructed from a
 * 200 response from GET /student endpoint.
 */
public class JSONStudent {
    public Integer id;
    public String usr;
    public String current_costume;
    public String nickname;
    public List<JSONCostume> costumes;
    public List<JSONAchievement> achievements;
}
