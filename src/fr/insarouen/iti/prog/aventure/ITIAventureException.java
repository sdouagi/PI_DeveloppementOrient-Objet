package fr.insarouen.iti.prog.aventure;


import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;
import fr.insarouen.iti.prog.aventure.elements.Entite;

public class ITIAventureException extends Exception {
    public ITIAventureException(){
    }

    public ITIAventureException(String msg){
        super(msg);
    }

    public ITIAventureException(String msg, Throwable cause){
        super(msg, cause);
    }
}