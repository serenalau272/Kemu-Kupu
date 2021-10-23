package com.models.APIModels;

/**
 * Used internally to define allowed interaction methods with the api.
 */
public enum RequestMethod {
    Post,
    Get,
    Delete;
    public String toString() {
        switch (this) {
            case Post:
                return "POST";
            case Get:
                return "GET";
            case Delete:
                return "DELETE";
        }
        return null;
    }
}
