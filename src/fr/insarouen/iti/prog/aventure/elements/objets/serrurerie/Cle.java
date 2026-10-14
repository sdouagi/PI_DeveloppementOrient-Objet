package fr.insarouen.iti.prog.aventure.elements.objets.serrurerie;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import java.lang.Object;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;

/**
 * Classe Objet
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public  class Cle extends Objet{

    // Constructeur
    /**
     * Constructeur de Objet à partir de son monde
     * 
     * @param monde : monde de l'entité
     */
    public Cle(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(monde.generateNom("clé"), monde);
    }

    @Override
    //Méthodes
    /**
     * Permet de savoir si l'objet est deplacable
     * 
     * @return booléen
     */
    public boolean estDeplacable(){
        return true;
    }
}