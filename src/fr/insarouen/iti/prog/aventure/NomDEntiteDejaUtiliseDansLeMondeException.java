package fr.insarouen.iti.prog.aventure; 

import fr.insarouen.iti.prog.aventure.MondeException;

public class NomDEntiteDejaUtiliseDansLeMondeException extends MondeException {
    public NomDEntiteDejaUtiliseDansLeMondeException(String message){
        super(message);
    }
    public NomDEntiteDejaUtiliseDansLeMondeException(String message, Throwable cause){
        super(message,cause);
    }    
}