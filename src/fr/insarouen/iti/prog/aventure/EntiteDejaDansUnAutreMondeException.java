package fr.insarouen.iti.prog.aventure; 

import fr.insarouen.iti.prog.aventure.MondeException;

public class EntiteDejaDansUnAutreMondeException extends MondeException {
    public EntiteDejaDansUnAutreMondeException(String message){
        super(message);
    }

    public EntiteDejaDansUnAutreMondeException(String message, Throwable cause){
        super(message,cause);
    }

}
