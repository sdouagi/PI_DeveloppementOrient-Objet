package fr.insarouen.iti.prog.aventure.elements.structure;
import fr.insarouen.iti.prog.aventure.elements.structure.ElementStructurelException;

public class PieceException extends ElementStructurelException{
    public PieceException(String message){
        super(message);

    }

    public PieceException(String message, Throwable cause){
        super(message,cause);
    }
}