package fr.insarouen.iti.prog.aventure.data.compil.ast;


public class Chaine extends Argument {
    private String chaine;

    public Chaine(String Chaine) {
        this.chaine = Chaine;
    }

    public String getChaine() {
        return this.chaine;
    }
}
