package com.models;

/**
 * Abstract interface to define a general modal user for both confirmation and
 * error modals
 */
public abstract interface GeneralModal {

    public abstract String getMessage();

    public abstract void doAction();

}