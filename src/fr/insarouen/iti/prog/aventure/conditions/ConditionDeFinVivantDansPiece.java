package fr.insarouen.iti.prog.aventure.conditions;

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe ConditionDeFinVivantDansPiece
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class ConditionDeFinVivantDansPiece extends ConditionDeFin{
   
    private Vivant vivant;
    private Piece piece;
   
   /**
     * Crée une condition liée à la présence d’un joueur dans une pièce.
     *
     * @param etat l’état du jeu.
     * @param vivant le joueur dans la pièce.
     * @param piece la pièce où le joueur présent.
     */
    public ConditionDeFinVivantDansPiece(EtatDuJeu etat, Vivant vivant, Piece piece){
        super(etat);
        this.vivant = vivant;
        this.piece = piece;
    }
    //Méthodes
    /**
     * Vérifie si le joueur est dans la pièce indiquée.
     *
     * @return l’état du jeu.
     */
    @Override
    public EtatDuJeu verifierCondition() {
        if (this.vivant.getPiece().getNom().equals(this.piece.getNom())) {
            return this.getEtatSiConditionVerifiee();
        }
        return EtatDuJeu.ENCOURS;
    }

}