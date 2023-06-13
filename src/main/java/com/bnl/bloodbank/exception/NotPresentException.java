package com.bnl.bloodbank.exception;

/**
 * To represent a data that is not present in database
 */
public class NotPresentException extends Exception{
    public NotPresentException(String msg){
        super(msg);
    }
}
