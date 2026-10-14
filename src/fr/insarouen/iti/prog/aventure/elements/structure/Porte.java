package fr.insarouen.iti.prog.aventure.elements.structure;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleAvecObjetException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleException;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.Activable;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure; 
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import java.io.Serializable;

/**
 * Classe Porte
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Porte extends ElementStructurel implements Activable, Serializable{

    private Piece pieceA;
    private Piece pieceB;
    private Etat etat;
    public Serrure serrure = null; 
//Constructeurs

    /**
     * Constructeur de Porte 
     * 
     * @param nom : chaine de caractères du nom de la porte 
     * @param monde : monde de l'entité
     * @param pieceA : la pieceA 
     * @param pieceA : la pieceB
     * @param serrure : serrure
     * 
     */
    public Porte(String nom, Monde monde, Serrure serrure, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(nom,monde);
        this.pieceA = pieceA;
        this.pieceB = pieceB; 
        this.etat = Etat.VERROUILLE;
        this.pieceA.addPorte(this);
        this.pieceB.addPorte(this);
        this.serrure = serrure; 
    }
        
    /**
     * Constructeur de Porte 
     * 
     * @param nom : chaine de caractères du nom de la porte 
     * @param monde : monde de l'entité
     * @param pieceA : la pieceA 
     * @param pieceA : la pieceB
     */
    public Porte(String nom, Monde monde, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(nom,monde);
        this.pieceA = pieceA;
        this.pieceB = pieceB; 
        this.etat = Etat.FERME;
        this.pieceA.addPorte(this);
        this.pieceB.addPorte(this);
    }
    
    //Méthodes

    /**
     * Permet d'obtenir l'etat de la porte 
     * 
     * @return Etat
     */
    public Etat getEtat(){
        return etat;
    }

    public Piece getPieceA() {
        return this.pieceA;
    }

    /**
     * Permet d'obtenir l'autre piece à coté
     * 
     * @return Piece
     */
    public Piece getPieceAutreCote(Piece piece){
        if (piece == pieceA){
            return pieceB;
        }
        else {
            return pieceA;
        }
    }

    @Override
    /**
     * Permet de changer l'etat de la porte 
     */
    public void activer() throws ActivationImpossibleException {
        if (this.serrure!= null && this.serrure.getEtat() == Etat.VERROUILLE){
            throw new ActivationImpossibleException(" la porte est verrouile activer impo");
        }
        this.etat = switch(this.etat){
            case Etat.FERME -> Etat.OUVERT;
            case Etat.OUVERT -> Etat.FERME;
            default -> Etat.OUVERT;
        }; 
    }

    @Override
    /**
     * Permet d'activé la porte avec un Objet
     * @param Objet : Objet pour activé une porte 
     */
    public void activerAvec(Objet objet) throws ActivationImpossibleAvecObjetException{
        if (serrure == null ){
            throw new ActivationImpossibleAvecObjetException(" pas de serrure donc activerAvecObj impo");
        }
        if (!this.activableAvec(objet)){
            System.out.println(String.format("la cle %s n'ouvre pas cette porte", objet.getNom()));
        }else{
            if (objet instanceof PiedDeBiche && this.getEtat()== Etat.VERROUILLE){
                this.etat =Etat.OUVERT;
                this.serrure=null;
            }
            if (objet instanceof Cle){
                this.serrure.activerAvec(objet);
                this.etat= switch(this.etat) {
                    case Etat.FERME -> Etat.VERROUILLE;
                    case Etat.OUVERT -> Etat.VERROUILLE;
                    case Etat.VERROUILLE -> Etat.OUVERT;
                    default -> Etat.OUVERT;
                };
            }
        }
    }

    public Serrure getSerrure(){
        return this.serrure;
    }


    @Override
    public boolean activableAvec(Objet objet){
        if (this.serrure == null){
                return false;
        }
        return ((objet instanceof PiedDeBiche) && this.etat.equals(Etat.VERROUILLE)) || this.serrure.activableAvec(objet);
    }

    /**
     * Redéfinition de toString
     * 
     * @return chaine de caractères décrivant l'entité
     */
    @Override
    public  String toString(){
        StringBuilder builder = new StringBuilder("Presentation de la porte: \n");
        builder.append(String.format("nom: %s%n  nom monde %s%n", this.getNom(), this.getMonde().getNom()));
        builder.append(String.format(" pieceA : %s%n", this.pieceA.getNom()));
        builder.append(String.format(" pieceB %s%n", this.pieceB.getNom()));
        builder.append(String.format(" état %s%n", this.getEtat()));
        return builder.toString();
        
    }

}