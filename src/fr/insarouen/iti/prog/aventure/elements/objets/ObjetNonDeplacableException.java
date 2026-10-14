package fr.insarouen.iti.prog.aventure.elements.objets;

import fr.insarouen.iti.prog.aventure.elements.objets.ObjetException;

public class ObjetNonDeplacableException extends ObjetException{
    public ObjetNonDeplacableException(String message){
        super(message);
    }

    public ObjetNonDeplacableException(String message, Throwable cause){
        super(message,cause);
    }   
}