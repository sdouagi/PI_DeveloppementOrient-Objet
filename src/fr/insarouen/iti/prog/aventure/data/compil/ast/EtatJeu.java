package fr.insarouen.iti.prog.aventure.data.compil.ast;

public class EtatJeu extends Argument {

    private String etat;

    public EtatJeu(String etat){
        this.etat = etat;
    }
    
    public String getEtat(){
        return this.etat;
    }
}
