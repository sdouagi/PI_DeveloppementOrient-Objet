package fr.insarouen.iti.prog.aventure;

import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.elements.Entite;

import java.util.Collection;
import java.util.ArrayList;


/**
 * Classe Simulateur
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Simulateur extends Object {

    private Monde monde;
    private Collection<ConditionDeFin> conditionsDeFin;
    private EtatDuJeu etatDuJeu;

    //Constructeurs

    /**
     * Construit un simulateur avec un monde donné et une collection de conditions de fin. 
     * 
     * @param monde : Le monde dans lequel le simulateur évolue
     * @param conditionDeFin: c'est une collection des conditions de fin a vérifier
     */

    public Simulateur(Monde monde, Collection<ConditionDeFin> conditionsDeFin) {
        this.monde = monde;
        this.etatDuJeu = EtatDuJeu.ENCOURS;
        if (conditionsDeFin == null) {
            this.conditionsDeFin = new ArrayList<>();
        } else {
            this.conditionsDeFin = conditionsDeFin;
        }
    }
    //Méthodes

    /**
     *  Joue un tour de jeuet met à jour l'état.
     *
     * @return l’état du jeu 
     * 
     */
    public EtatDuJeu executerUnTour() throws ITIAventureException {
        if (this.etatDuJeu != EtatDuJeu.ENCOURS) {
            if (this.etatDuJeu == EtatDuJeu.SUCCES){
                System.out.println("Bravo vous avez réussi !");
            }
            
            if (this.etatDuJeu == EtatDuJeu.ECHEC){
                System.out.println("Oh non c'est perdu!");
            }
            return this.etatDuJeu;
        }

        Collection<Entite> entites = this.monde.getEntites();
        this.afficherSituationJoueur(entites);

        for (Entite e : entites) {
            if (e instanceof JoueurHumain) {
                JoueurHumain joueur = (JoueurHumain) e;
                joueur.executer();
            }
        }

        for (ConditionDeFin c : conditionsDeFin) {
            EtatDuJeu nvEtat = c.verifierCondition();
            if (nvEtat != EtatDuJeu.ENCOURS) {
                this.etatDuJeu = nvEtat;
                return nvEtat;
            }
        }

        return this.etatDuJeu;
    }


    /*
     * Exécute les tours automatiquement jusqu’à la fin du jeu.
     * Affiche un message indiquent la résultat.
     *
    */
    public void executerJusquALaFin() throws ITIAventureException{
        EtatDuJeu etat = this.etatDuJeu;
        while (etat == EtatDuJeu.ENCOURS) {
            etat = this.executerUnTour();
        }
        if (etat == EtatDuJeu.SUCCES){
            System.out.println("Bravo vous avez réussi !");
        }
        
        if (etat == EtatDuJeu.ECHEC){
            System.out.println("Oh non c'est perdu!");
        }
    }

    /**
     * Affiche la situation actuelle du joueur :
     * son état, la pièce où il se trouve et les portes de cette pièce.
     *
     * @param entites collection d'entités .
     */
    private void afficherSituationJoueur(Collection<Entite> entites)  {
        for (Entite entite : entites) {
            if (entite instanceof JoueurHumain) {
                JoueurHumain joueur = (JoueurHumain) entite;
                System.out.println("\nJoueur : ");
                System.out.println(joueur);

                System.out.println("\nPièce du joueur : ");
                Piece piece = joueur.getPiece();
                System.out.println(piece);

                System.out.println("\nPortes de la pièce : ");
                for (Porte p : piece.getPortes()) {
                    System.out.println(p);
                }
            }
        }
    }
}
