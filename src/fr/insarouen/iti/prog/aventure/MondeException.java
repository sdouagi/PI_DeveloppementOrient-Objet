package fr.insarouen.iti.prog.aventure;

import fr.insarouen.iti.prog.aventure.ITIAventureException;

public class MondeException extends ITIAventureException {
    public MondeException (String message){
        super(message);
    }

    public MondeException(String message, Throwable cause){
        super(message,cause);
    }
}   
