package fr.insarouen.iti.prog.aventure.elements.structure;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;

/**
 * Classe ElementStructurel
 * 
 * @author Selma DOUAGI et Anas YOMNI
 */

public abstract class ElementStructurel extends Entite {
	
	// Constructeur
    /**
     * Constructeur de ElementStruturel à partir de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     */
	public ElementStructurel(String nom,Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
		super(nom,monde);
	}

}
