package fr.insarouen.iti.prog.aventure.conditions; 

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;

/**
 * Classe ConditionDeFinVivantMort
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class ConditionDeFinVivantMort extends ConditionDeFin{ 

    private Vivant vivant;

    // Constructeur
    /**
     * Crée une condition liée à la mort d’un joueur.
     *
     * @param etat l’état du jeu si le joueur est mort.
     * @param vivant le joueur .
     */
    public ConditionDeFinVivantMort(EtatDuJeu etat, Vivant vivant){
        super(etat);
        this.vivant = vivant;
    }

    /**
     * Vérifie si le personnage est mort.
     * 
     *
     * @return l’état de jeu 
     */
    @Override
    public EtatDuJeu verifierCondition(){
        if (this.vivant.estMort()) {
            return this.getEtatSiConditionVerifiee();
        }
        return EtatDuJeu.ENCOURS;
    }

}