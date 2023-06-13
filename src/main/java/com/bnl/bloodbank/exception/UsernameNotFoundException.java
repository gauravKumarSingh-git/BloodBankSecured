package com.bnl.bloodbank.exception;

/**
 * To represent that provided username is not found in database
 */
public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException(String msg){
        super(msg);
    }
}
