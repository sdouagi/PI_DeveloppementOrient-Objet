package fr.insarouen.iti.prog.aventure.conditions;

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantDansPiece;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantPossedeObjets;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe ConditionDeFinVivantDansPieceEtPossedeObjets
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class ConditionDeFinVivantDansPieceEtPossedeObjets extends ConditionDeFin{

    private ConditionDeFinVivantDansPiece cdfvdp;
    private ConditionDeFinVivantPossedeObjets cdfvpo;

    /**
     * Crée une condition de fin combinée : présence dans une pièce et possession d’objets.
     *
     * @param etat l’état du jeu 
     * @param vivant le joueur concerné
     * @param piece la pièce où se trouver le joueur
     * @param objets les objets que le joueur doit posséder
     */
    public ConditionDeFinVivantDansPieceEtPossedeObjets(EtatDuJeu etat, Vivant vivant, Piece piece, Objet... objets){
        super(etat);
        cdfvdp = new ConditionDeFinVivantDansPiece(etat, vivant, piece);
        cdfvpo = new ConditionDeFinVivantPossedeObjets(etat, vivant, objets);
    }

    //Méthodes
    /**
     * Vérifie si le personnage est dans  pièce et possède les objets.
     *
     * @return l’état du jeu 
     */
    @Override
    public EtatDuJeu verifierCondition(){
        if (cdfvdp.verifierCondition()==EtatDuJeu.ENCOURS|| cdfvpo.verifierCondition()==EtatDuJeu.ENCOURS){
            return EtatDuJeu.ENCOURS;
        }
        else{
            return getEtatSiConditionVerifiee();
        }
    }
}