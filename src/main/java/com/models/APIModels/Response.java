package com.models.APIModels;

/**
 * Represents a simple api response, containing relevant data and methods
 */
public class Response {
    private String body;
    private String path;
    private ResponseStatus status;

    public Response(String data, ResponseStatus status, String path) throws IllegalArgumentException {
        if (data == null) 
            throw new IllegalArgumentException("Data may not be null");
        if (path == null)
            throw new IllegalArgumentException("Path may not be null");
        this.body = data;
        this.status = status;
        this.path = path;
    }

    /**
     * Parse the response body from json to it's contents
     * @return the parsed json data as a string, can be coreced into an object if needed using gson
     */
    public String loadJsonData() {
        return this.body.substring(10, this.body.length()-2);
    }

    /**
     * Parse the response as raw json, rather than just the raw string. This shoudl be used if there is a sub
     * object that needs to be parsed.
     * @return a string to be parsed.
     */
    public String loadJsonDataRaw() {
        return this.body.substring(9, this.body.length()-1);
    }

    public String getBody() {
        return this.body;
    }

    public String getPath() {
        return this.path;
    }

    public ResponseStatus getStatus() {
        return this.status;
    }
}