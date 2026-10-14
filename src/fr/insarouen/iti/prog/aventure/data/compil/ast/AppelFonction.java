package fr.insarouen.iti.prog.aventure.data.compil.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique.SemantiqueErreurException;
import fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique.NombreParametreErreurException;
import fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique.TypeException;

import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visitable;
import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visiteur;

public class AppelFonction extends Argument implements Visitable{

    private NomFonction nomF;
    private List<Argument> args;

    public AppelFonction(NomFonction nomF, Collection<Argument> args) {
        this.nomF = nomF;
        this.args = new ArrayList<Argument>(args);
    }

    public NomFonction getNomFonction(){
        return this.nomF;
    }

    public List<Argument> getArguments(){
        return List.copyOf(this.args);
    }
    
    @Override
    public void accepter(Visiteur v) throws SemantiqueErreurException {
        switch (this.getNomFonction()) {
            case monde:
                this.analyseSemantique(this.getArguments(), 1, true, Chaine.class);
                break;
            case piece:
                this.analyseSemantique(this.getArguments(), 2, true, Identifiant.class, Chaine.class);
                break;
            case porte:
                if (this.getArguments().size() == 4) {
                    this.analyseSemantique(this.getArguments(), 4, true, Identifiant.class, Chaine.class, Identifiant.class, Identifiant.class);
                } else if (this.getArguments().size() == 5) {
                    this.analyseSemantique(this.getArguments(), 5, true, Identifiant.class, Chaine.class, Identifiant.class, Identifiant.class, AppelFonction.class);
                } else {
                    throw new NombreParametreErreurException("Pas le bon nombre de parametres");
                }
                break;
            case humain:
                this.analyseSemantique(this.getArguments(), 5, false, Identifiant.class, Chaine.class, Entier.class, Entier.class, Identifiant.class);
                break;
            case serrure:
                this.analyseSemantique(this.getArguments(), 1, false, Identifiant.class);
                break;
            case cle:
                this.analyseSemantique(this.getArguments(), 2, true, Identifiant.class, Identifiant.class);
                break;
            case cdf_vivant_dans_piece:
                this.analyseSemantique(this.getArguments(), 3, true, EtatJeu.class, Identifiant.class, Identifiant.class);
                break;
            //monstre avec comme paramètre le monde, le nom, le nombre de point de vie, le nombre de point de force et la pièce où il se trouve au départ et optionnellement autant d'objets qu'il possède
            case monstre:
                List<Argument> arguments = this.getArguments();

                this.analyseSemantique(this.getArguments(), 5, true, Identifiant.class, Chaine.class, Entier.class, Entier.class, Identifiant.class);
                break;

            //pied_de_biche avec comme paramètre le monde et le nom du pied de biche
            case pied_de_biche:
                this.analyseSemantique(this.getArguments(), 2, true, Identifiant.class, Chaine.class);
                break;
            //cdf_vivant_possede avec comme paramètre l'état, l'humain ou le monstre et l'objet;
            case cdf_vivant_possede:
                this.analyseSemantique(this.getArguments(), 3, true, EtatJeu.class, Identifiant.class, Identifiant.class);
                break;
            //cdf_conjonction avec comme paramètre aux moins deux conditions de fin
            //case cdf_conjonction:
            //    this.analyseSemantique(this.getArguments(), 2, true, EtatJeu.class, EtatJeu.class);
            //    break;
            default:
                break;
        }
    }

    private void analyseSemantique(Collection<Argument> args, int nbParametresMin, Boolean nbParametresFixe, Class<? extends Argument>... typesAttendus) throws SemantiqueErreurException {
        List<Argument> listeArguments = new ArrayList<>(args);
        
         //On vérifie le nombre de parametre
        if ((nbParametresFixe && listeArguments.size() < nbParametresMin)){
            throw new NombreParametreErreurException("Le nombre de parametre est incorrect");
        }

        for (int i = 0; i < nbParametresMin; i++) {
            if (!typesAttendus[i].isInstance(listeArguments.get(i))) {
                throw new TypeException(String.format("Type incorrect 0: attendu : %s donné : %s", typesAttendus[i].toString(), listeArguments.get(i).getClass().toString()));
            }
        }

        if (!nbParametresFixe) {
            for (int i = nbParametresMin; i < listeArguments.size(); i++) {
                if (!(listeArguments.get(i) instanceof Identifiant || listeArguments.get(i) instanceof AppelFonction)) {
                    throw new TypeException(String.format("Type incorrect 0: attendu : %s donné : %s", typesAttendus[i].toString(), listeArguments.get(i).getClass().toString()));
                }
            } 
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.nomF + "(");
        for (Argument a : this.args){
            str.append(a.toString()).append(",");
        }
        str.append(")");
        return str.toString();
    }
}