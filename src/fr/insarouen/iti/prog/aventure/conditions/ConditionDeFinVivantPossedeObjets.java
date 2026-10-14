package fr.insarouen.iti.prog.aventure.conditions;

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

/**
 * Classe ConditionDeFinVivantPossedeObjets
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class ConditionDeFinVivantPossedeObjets extends ConditionDeFin {
    
    private Vivant vivant;
    private  Collection<Objet> objets;

    /**
     * Crée une condition de fin vérifiant que le joueur possède tous les objets indiqué.
     *
     * @param etat l’état du jeu 
     * @param vivant le joueur concerné
     * @param objets les objets que le joueur doit posséder
     */
    public ConditionDeFinVivantPossedeObjets(EtatDuJeu etat, Vivant vivant, Objet... objets){
        super(etat);
        this.vivant = vivant;
        this.objets = new ArrayList<Objet>(Arrays.asList(objets));
    }

    /**
     * Vérifie si le personnage possède tous les objets requis.
     *
     * @return l’état du jeu.
     */
    @Override
    public EtatDuJeu verifierCondition(){
        EtatDuJeu etat = this.getEtatSiConditionVerifiee() ;
        if (this.vivant.getObjets().containsAll(this.objets)){
                etat = EtatDuJeu.ENCOURS;
        }
        return etat;
    }

}
