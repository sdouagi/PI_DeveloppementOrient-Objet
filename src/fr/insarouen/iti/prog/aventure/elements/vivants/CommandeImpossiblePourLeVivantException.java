package fr.insarouen.iti.prog.aventure.elements.vivants;

import fr.insarouen.iti.prog.aventure.elements.vivants.VivantException;

public class CommandeImpossiblePourLeVivantException extends VivantException{
    public CommandeImpossiblePourLeVivantException(String message){
        super(message);
    }

    public CommandeImpossiblePourLeVivantException(String message, Throwable cause){
        super(message,cause);
    }   
}