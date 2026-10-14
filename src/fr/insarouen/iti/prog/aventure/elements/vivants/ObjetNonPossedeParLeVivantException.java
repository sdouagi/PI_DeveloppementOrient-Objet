package fr.insarouen.iti.prog.aventure.elements.vivants;

import fr.insarouen.iti.prog.aventure.elements.vivants.VivantException;

public class ObjetNonPossedeParLeVivantException extends VivantException{
    public ObjetNonPossedeParLeVivantException(String message){
        super(message);
    }

    public ObjetNonPossedeParLeVivantException(String message, Throwable cause){
        super(message,cause);
    }   
}