package fr.insarouen.iti.prog.aventure.data.compil.ast;

import java.util.ArrayList;
import java.util.List;
import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visitable;
import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visiteur;

public class Configuration implements Visitable{

    private List<Affectation> affectations =new ArrayList<>();

    public Configuration(Affectation ...affectations){
        if (affectations != null){
            for ( Affectation aF : affectations){
                this.ajouterAffectation(aF);
            }
        }
    }

    public void ajouterAffectation(Affectation aff){
        if (aff != null) {
            this.affectations.add(aff);
        }
    }

    public List<Affectation> getAffectations(){
        return this.affectations;
    }

    @Override
    public void accepter(Visiteur v) throws Throwable {
        v.visiter(this);
    }

    @Override
    public String toString() {
        String str ="";
        for (Affectation a : this.affectations){
            str += a.toString() +"\n";
        }
        return str;
    }
}