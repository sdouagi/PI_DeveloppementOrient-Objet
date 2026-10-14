package fr.insarouen.iti.prog.aventure.data.compil.ast;

public class Entier extends Argument {

    private int valeur;

    public Entier(int valeur){
        this.valeur = valeur;
    }
    
    public int getValeur(){
        return this.valeur;
    }
}
