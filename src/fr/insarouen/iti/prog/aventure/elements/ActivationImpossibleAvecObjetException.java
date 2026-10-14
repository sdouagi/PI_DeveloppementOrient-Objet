package fr.insarouen.iti.prog.aventure.elements;

public class ActivationImpossibleAvecObjetException extends ActivationImpossibleException {
    public ActivationImpossibleAvecObjetException (String message){
        super(message);
    }

    public ActivationImpossibleAvecObjetException(String message, Throwable cause){
        super(message,cause);
    }
}   
