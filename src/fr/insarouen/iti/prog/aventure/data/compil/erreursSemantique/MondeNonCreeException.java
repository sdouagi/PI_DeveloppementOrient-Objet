package fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique;

import fr.insarouen.iti.prog.aventure.ITIAventureException;

public class MondeNonCreeException extends ITIAventureException {
    public MondeNonCreeException (String message){
        super(message);
    }

    public MondeNonCreeException(String message, Throwable cause){
        super(message,cause);
    }
}  
