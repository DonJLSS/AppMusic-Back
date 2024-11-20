package com.aunnait.appmusic.exceptions;

//Custom exception class
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
