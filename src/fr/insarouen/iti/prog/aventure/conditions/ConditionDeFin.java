package fr.insarouen.iti.prog.aventure.conditions; 

import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import java.io.Serializable;

/**
 * Classe ConditionDeFin
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public abstract class ConditionDeFin implements Serializable{

    private EtatDuJeu etatCondition;

    /**
     * Construit une condition de fin avec l’état donnée.
     *
     * @param etat l’état de jeu.
     */
    public ConditionDeFin(EtatDuJeu etat){
        this.etatCondition = etat;
    }
    /**
     * Indique l’état associé à cette condition de fin.
     *
     * @return l’état du jeu 
     */
    public EtatDuJeu getEtatSiConditionVerifiee(){
        return this.etatCondition;
    }

    public abstract EtatDuJeu verifierCondition();
}