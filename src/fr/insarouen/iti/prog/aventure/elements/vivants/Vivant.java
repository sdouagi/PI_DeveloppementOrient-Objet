package fr.insarouen.iti.prog.aventure.elements.vivants;


import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteFermeException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe Vivant
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public abstract class Vivant extends Entite implements Serializable{
    
 
    private Map<String, Objet> objets  = new HashMap<>();
    private int pointVie;
    private int pointForce;
    private Piece piece;

    // Constructeur

    /**
     * Constructeur de Vivant à partir de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     * @param pointVie : point de vie du vivant
     * @param pointForce : point de force du vivant
     * @param piece :  piece dans laquelle se situe le vivant
     * @param tabObjet : les objets que  possède le vivant
     */
    public Vivant(String nom, Monde monde, int pointVie, int pointForce, Piece piece, Objet... objets) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException{
        super(nom, monde);
        this.pointVie = pointVie;
        this.pointForce = pointForce;
        this.setPiece(piece);
        for (int i=0; i<objets.length;i++){
            this.objets.put(objets[i].getNom(),objets[i]);
        }
    }

    //Méthodes

    /**
     * Permet de savoir si un objet dans l'inventaire du vivant
     * 
     * @param objet : objet recherché
     * 
     * @return booleen
     */
    public boolean possede(Objet objet){
        if (objet == null)
            return false;
        return this.objets.containsKey(objet.getNom());

    }

    public boolean possede(String nomObjet){
        if (nomObjet == null)
            return false;
        return this.objets.containsKey(nomObjet);        
    }

    /**
     * Permet de récupérer un objet
     * 
     * @param objet : objet à prendre
     */
    public void prendre(Objet objet) throws ObjetAbsentDeLaPieceException,ObjetNonDeplacableException{
        if (objet == null){
            throw new ObjetAbsentDeLaPieceException("objet absent de la piece");
        }
        if (!objet.estDeplacable()){
            throw new ObjetNonDeplacableException(String.format("Objet %s non déplacable !", objet.getNom()));
        }
        if (this.piece.contientObjet(objet)){
            this.piece.retirer(objet);
            this.objets.put(objet.getNom(),objet);
        }
    }

    /**
     * Permet de récupérer un objet à partir de son nom
     * 
     * @param nomObjet : nom de l'objet à prendre
     */
    public void prendre(String nomObjet) throws ObjetAbsentDeLaPieceException,ObjetNonDeplacableException{
        this.prendre(this.piece.getObjet(nomObjet));
    }

    /**
     * Permet de déposer un objet de l'inventaire du vivant
     * 
     * @param objet : objet à déposer
     */
    public void deposer(Objet objet) throws ObjetNonPossedeParLeVivantException{
        if (!this.possede(objet)){
            throw new ObjetNonPossedeParLeVivantException(" le vivant ne possède pas cet objet !");
        }
        else {
            this.piece.deposer(objet);
            this.objets.remove(objet.getNom());
        }
    }

    /**
     * Permet de déposer un objet de l'inventaire du vivant
     * 
     * @param nomObjet : nom de l'objet à déposer
     */
    public void deposer(String nomObjet) throws ObjetNonPossedeParLeVivantException{ 
        if (!this.possede(nomObjet)){
            throw new ObjetNonPossedeParLeVivantException(" le vivant ne possède pas cet objet !");
        }
        this.deposer(this.getObjet(nomObjet));
    }

    /**
     *  met la piece dans laquelle le vivant est
     * 
     * @param piece : piece souhaitée
     */
    public void setPiece(Piece piece) throws VivantAbsentDeLaPieceException {
        if (piece != null) {
            piece.entrer(this);
            this.piece = piece;
        }
        else {
            Piece pieceCourante = this.piece;
            if (pieceCourante != null && pieceCourante.contientVivant(this)) {
                try {
                    pieceCourante.sortir(this);
                }
                catch(VivantAbsentDeLaPieceException ex) {
                    throw new Error("Ne devrait pas arriver 2", ex);
                }
            }
            this.piece = piece;
        }
        this.piece = piece;
    }
    
    
    /**
     * Permet d'obtenir un objet à partir de son nom si il est dans l'inventaire
     * 
     * @param nomObjet : nom de l'objet recherché
     * 
     * @return Objet
     */
    public Objet getObjet(String nomObjet){
        return this.objets.get(nomObjet);
    }

    /**
     * Permet d'obtenir la liste des objets de l'inventaire du vivant
     * 
     * @return un tableau contenant les objets de l'inventaire
     */
    public Collection<Objet> getObjets() {
        return Collections.unmodifiableCollection(this.objets.values());
    }

    /**
     * Permet de fixer les points de vie du vivant
     * 
     * @param points de vie du vivant
     */
    public void setPointVie(int points){
        this.pointVie = points;
    }

    /**
     * Permet de fixer les points de force du vivant
     * 
     * @param points de force du vivant
     */
    public void setPointForce(int points){
        this.pointForce = points;
    }


    /**
     * Permet d'obtenir les points de vie du vivant
     * 
     * @return point de vie du vivant
     */
    public int getPointVie(){
        return this.pointVie;
    }

    /**
     * Permet d'obtenir les points de force du vivant
     * 
     * @return points de force du vivant
     */
    public int getPointForce(){
        return this.pointForce;
    }

    /**
     * Permet d'obtenir la pièce du vivant
     * @return piece du vivant
     */
    public Piece getPiece(){
        return this.piece;
    }

    /**
     * Permet de savoir si le vivant est mort
     * 
     * @return  booleen
     */
    public boolean estMort(){
        return (this.pointVie == 0);
    }

    /**
     * permet au vivant de franchier une porte et donc de changer de piece
     * 
     * @param porte : la porte souhaitée
     */
    public void franchir(Porte porte)throws PorteInexistanteDansLaPieceException, PorteFermeException, VivantAbsentDeLaPieceException {
        if (!this.piece.aLaPorte(porte)){
            throw new PorteInexistanteDansLaPieceException("la porte n'existe pas dans la piece ! ");
        } 
        if (porte.getEtat()==Etat.FERME){
            throw new PorteFermeException("la porte est fermée");
        }
        this.setPiece(porte.getPieceAutreCote(this.piece));
    }

    /**
     * permet au vivant de franchier une porte et donc de changer de piece
     * 
     * @param nomPorte : nom de la porte souhaitée
     */
    public void franchir(String nomPorte)throws PorteInexistanteDansLaPieceException, PorteFermeException, VivantAbsentDeLaPieceException{ 
        this.franchir(this.piece.getPorte(nomPorte));
    }

    /**
     * Redéfinition de la méthode toString.
     * Retourne une chaîne de caractères représentant l'état du vivant.
     * 
     * @return La chaîne de caractères représentant le vivant.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Présentation du Vivant :\n");
        builder.append(String.format("Nom : %s%nMonde : %s%n", this.getNom(), this.getMonde().getNom()));

        if (this.objets.isEmpty()) {
            builder.append("Aucun objet contenu.\n");
        } else {
            builder.append("Objets contenus :\n");
            for (Objet obj : this.objets.values()) {
                builder.append(String.format("-> %s%n", obj.getNom()));
            }
        }

        if (this.getPiece() == null){
            builder.append(String.format("Vivant dans AUCUNE pièce"));
        } else {
            builder.append(String.format("Vivant dans la pièce : %s%n", this.getPiece().getNom()));
        }
        return builder.toString();
    }


    public void executer() throws ITIAventureException {

    }
    
}