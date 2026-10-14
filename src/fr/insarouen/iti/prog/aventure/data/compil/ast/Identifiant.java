package fr.insarouen.iti.prog.aventure.data.compil.ast;

public class Identifiant extends Argument {

    private String nom;

    public Identifiant(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }
}
