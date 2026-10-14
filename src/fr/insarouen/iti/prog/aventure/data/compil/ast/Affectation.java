package fr.insarouen.iti.prog.aventure.data.compil.ast;

import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visitable;
import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visiteur;

public class Affectation implements Visitable{
    private String nomVar;
    private AppelFonction f;

    public Affectation(Identifiant nomVar , AppelFonction f) {
        this.nomVar = nomVar.getNom();
        this.f = f;
    }

    public String getNomVar(){
        return this.nomVar;
    }

    public AppelFonction getAppelFonction(){
        return this.f;
    }
    
    @Override
    public void accepter(Visiteur visiteur) throws Throwable{
        visiteur.visiter(this);
    }

    @Override
    public String toString() {
        return nomVar.toString() + "=" + f.toString();
    }
}