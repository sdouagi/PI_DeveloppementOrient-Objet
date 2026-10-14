package fr.insarouen.iti.prog.aventure.elements.objets;
import fr.insarouen.iti.prog.aventure.Monde;
import java.lang.Object;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;

/**
 * Classe Objet
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public abstract class Objet extends Entite{
	
    protected boolean deplacable = false;
       
    // Constructeur
    /**
     * Constructeur de Objet à partir de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     */
    public Objet(String nom ,Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(nom,monde);
    }

    //Méthodes

    /**
     * Permet de savoir si l'objet est deplacable
     * 
     * @return booléen
     */
    public abstract boolean estDeplacable();

    /**
     * Redéfinition de toString
     * 
     * @return chaine de caractères décrivant l'entité
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("Presentation de la piece: \n");
        builder.append(String.format("nom: %s%n nom monde %s%n", this.getNom(), this.getMonde().getNom()));
        builder.append( String.format("%s déplacable",estDeplacable()?"est" : "n'est pas"));
        return builder.toString();
    }

    /**
     * Redéfinition de equals
     * 
     * @return boolean
     */
    public boolean equals(Object o){
        if (this== o){
            return true ;
        }
        if (o ==null || this.getClass()!=o.getClass()){
            return false;
        }
        return super.equals(o);
    }
}
