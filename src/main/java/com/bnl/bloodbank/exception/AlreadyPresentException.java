package com.bnl.bloodbank.exception;

/**
 * To represent a data that is already present in database
 */
public class AlreadyPresentException extends Exception {
    public AlreadyPresentException(String msg){
        super(msg);
    }
}
