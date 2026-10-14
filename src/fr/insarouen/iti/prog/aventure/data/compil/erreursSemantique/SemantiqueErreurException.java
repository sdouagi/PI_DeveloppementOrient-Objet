package fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique;

public class SemantiqueErreurException extends Exception {

    public SemantiqueErreurException(String message) {
        super(message);
    }

    public SemantiqueErreurException(){
        super();
    }
}