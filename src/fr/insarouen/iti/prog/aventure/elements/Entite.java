package fr.insarouen.iti.prog.aventure.elements;

import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.Monde;
import java.lang.Object;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;
import java.io.Serializable;

/**
 * Classe Entite
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */

public abstract class Entite implements Serializable{

    private String nom;
    private Monde monde;
    //Constructeurs

    /**
     * Constructeur d'Entité avec de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     */
    public Entite(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        this.nom = nom;
        this.monde = monde;
        if (monde.getEntite(nom) != null) {
            throw new NomDEntiteDejaUtiliseDansLeMondeException("Une entité avec le nom '" + nom + "' existe déjà dans ce monde.");
        }
        try{
            this.monde.ajouterEntite(this);
        }
        catch(EntiteDejaDansUnAutreMondeException e) {
            throw new Error (" ajout d'une entite deja d’un autre monde");
        }
        
    }
    //Méthodes

    /**
     * Permet d'obtenir le nom de l'entité
     * 
     * @return chaine de caractères du nom de l'entité
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Permet d'obtenir le monde de l'entité
     * 
     * @return monde de l'entité
     */
    public Monde getMonde(){
        return this.monde;
    }

    /**
     * Redéfinition de toString
     * 
     * @return chaine de caractères décrivant l'entité
     */
    public  String toString(){
        return String.format("nom : %s\nmonde : %s",this.nom,this.monde);
    }

    /**
     * Permet de vérifier l'égalité entre deux entités
     * 
     * @param o de type Object
     * @return Boolean qui nous indique si l'égalité est vérifié
     */
    @Override
    public boolean equals(Object o) {
        
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Entite autreEntite = (Entite) o;

        return (this.nom.equals(autreEntite.getNom()) && this.monde.equals(autreEntite.getMonde()));

    }

    /**
     * hashCode redéfinie
     * @return le hashCode de l'entité
     */
    @Override
    public int hashCode() {
        return 2*this.getNom().hashCode() + 3*this.getMonde().hashCode();
    }
    
}
