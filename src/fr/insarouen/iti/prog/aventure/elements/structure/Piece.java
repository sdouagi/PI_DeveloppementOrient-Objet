package fr.insarouen.iti.prog.aventure.elements.structure;


import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe Entite
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Piece extends ElementStructurel implements Serializable{
    private HashMap<String, Objet> objets  = new HashMap<>();
    private HashMap<String, Vivant> vivants  = new HashMap<>();
    private HashMap<String, Porte> portes  = new HashMap<>();

    //Constructeurs

    /**
     * Constructeur d'Entité avec de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     */
    public Piece(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        super(nom,monde);
    }

    //Méthodes

    /**
     * Permet d'obtenir un objet à partir de son nom si il est dans la piece
     * 
     * @param nomObjet : nom de l'objet recherché
     * 
     * @return Objet
     */
    public Objet getObjet(String nomObjet){
        return this.objets.get(nomObjet);
    }

    /**
     * Permet d'obtenir la liste des objets de la pièce
     * 
     * @return un tableau contenant les objets de la pièce
     */
    public Collection<Objet> getObjets() {
        return Collections.unmodifiableCollection(this.objets.values());
    }

    /**
     * Permet d'obtenir la liste des vivants de la pièce
     * 
     * @return un tableau contenant les vivants de la pièce
     */
    public Collection<Vivant> getVivants() {
        return Collections.unmodifiableCollection(this.vivants.values());
    }

    /**
     * Permet d'obtenir un vivant à partir de son nom si il est dans la piece
     * 
     * @param nomVivant : nom du vivant recherché
     * 
     * @return Objet
     */
    public Vivant getVivant(String nomVivant){
        return this.vivants.get(nomVivant);
    }

    /**
     * Permet d'obtenir un vivant à partir de son nom si il est dans la piece
     * 
     * @param nomVivant : nom du vivant recherché
     * 
     * @return Objet
     */
    public Porte getPorte(String nomPorte){
        return this.portes.get(nomPorte);
    }


    public Collection<Porte> getPortes() {
        return Collections.unmodifiableCollection(this.portes.values());
    }

    /**
     * Permet de savoir si un objet est dans la pièce à partir de son nom
     * 
     * @param objet : objet recherche
     * @return booleen
     */
    public boolean contientObjet(Objet objet){
        return this.objets.containsKey(objet.getNom());
    }

    /**
     * Permet de savoir si un objet est dans la pièce à partir de son nom
     * 
     * @param objet : no de l'objet recherche
     * @return booleen
     */
    public boolean contientObjet(String nomObjet){
        return this.objets.containsKey(nomObjet);
    }

    /**
     * Permet de déposer un objet dans la pièce
     * 
     * @param objet : objet à déposer
     */
    public void deposer(Objet objet){
        this.objets.put(objet.getNom(),objet);
    }

    /**
     * Permet de retirer un objet de la pièce
     * 
     * @param objet : objet à retirer
     * 
     * @return l'objet retirer ou null s'il n'a pas ete retirer
     */
    public Objet retirer(Objet objet) throws ObjetNonDeplacableException, ObjetAbsentDeLaPieceException{
        if (!contientObjet(objet)){
            throw new ObjetAbsentDeLaPieceException("objet à retirer n'est pas pas dans la piece");
        }
        if (!objet.estDeplacable()){
            throw new ObjetNonDeplacableException(" objet à retirer n'est pas déplacable"); 
        }
        return this.objets.remove(objet.getNom());
    }

    /**
     * Permet de retirer un objet de la pièce
     * 
     * @param nom : nom de l'objet à retirer
     * 
     * @return l'objet retirer ou null s'il n'a pas ete retirer
     */
    public Objet retirer(String nom) throws ObjetNonDeplacableException, ObjetAbsentDeLaPieceException {
        return this.retirer(this.getObjet(nom));
    }

    /**
     * Permet de savoir si un vivant est dans la pièce
     * 
     * @param nomVivant : nom du vivant recherché
     * 
     * @return booleen
     */
    public boolean contientVivant(String nomVivant){
        return this.vivants.containsKey(nomVivant);
    }

    /**
     * Permet de savoir si un vivant est dans la pièce
     * 
     * @param vivant : vivant recherché
     * 
     * @return booleen
     */
    public boolean contientVivant(Vivant vivant){
        return this.vivants.containsKey(vivant.getNom());
    }

    /**
     * Permet d'ajouter un vivant dans le tableau de vivant
     * 
     * @param nomVivant : nom du vivant recherché
     * 
     */
    public void ajouterVivant(Vivant vivant)throws VivantAbsentDeLaPieceException{
        if  (!this.contientVivant(vivant)){
            this.vivants.put(vivant.getNom(),vivant);
            vivant.setPiece(this); 
        }
    }


    /**
     * Permet à un vivant d'entrer dans la pièce
     * 
     * @param vivant : vivant entrant
     * @throws VivantAbsentDeLaPieceException 
     */
    public void entrer(Vivant vivant) throws VivantAbsentDeLaPieceException {
        if (!this.contientVivant(vivant)) {
            if (vivant.getPiece() != null) {
                try {
                    vivant.getPiece().sortir(vivant);
                }
                catch (VivantAbsentDeLaPieceException ex) {
                    throw new Error("Ne devrait pas arriver 1", ex);
                }
            }
        }
        this.ajouterVivant(vivant);

    }

    /**
     * Permet de faire sortir un vivant de la pièce à partir de son nom
     * 
     * @param nomVivant : nom du vivant sortant
     */

    public Vivant sortir(String nomVivant) throws VivantAbsentDeLaPieceException {
        Vivant v= vivants.get(nomVivant);
        
        if (v == null) {
            throw new VivantAbsentDeLaPieceException(
            String.format("Le vivant %s n'est pas dans la pièce %s", nomVivant, this.getNom()));
        }
        vivants.remove(nomVivant);
        v.setPiece(null);
        return v;
}

    /**
     * Permet à un vivant de sortir de la pièce
     * 
     * @param vivant : vivant sortant
     */
    public Vivant sortir(Vivant vivant) throws VivantAbsentDeLaPieceException {
        if (vivant == null) {
            throw new IllegalArgumentException("Le vivant ne peut pas être null");
        }
        return this.sortir(vivant.getNom());
    }

    /**
     * Permet de savoir si une porte est dans la pièce
     * 
     * @param porte : porte recherché
     * 
     * @return booleen
     */
    public boolean aLaPorte(Porte porte){
        return this.portes.containsValue(porte);
    }
    
    /**
     * Permet de savoir si une porte est dans la pièce
     * 
     * @param porte : porte recherché
     * 
     * @return booleen
     */
    public boolean aLaPorte(String nomPorte){
        return this.portes.containsKey(nomPorte);
    }


    /**
     * Permet d'ajouter une porte dans la Piece
     * 
     * @param porte : Porte à ajouter 
     * 
     */
    protected void addPorte(Porte porte){
        if  (!this.aLaPorte(porte)){
            this.portes.put(porte.getNom(),porte);
        }
    }

    /**
     * Redéfinition de toString
     * 
     * @return chaine de caractères décrivant l'entité
     */
    @Override
    public String toString(){
        String tmp= super.toString();
        tmp = String.format("%s\n%s\n",tmp,"\nobjets de la pièce: ");
        Set<String> nom_objets = this.objets.keySet();
        for(String i : nom_objets)
                tmp = String.format("%s%s, ",tmp,i);
        tmp = String.format("%s\n%s\n",tmp,"\nvivants de la pièce : ");
        Set<String> nom_vivants = this.vivants.keySet();
        for(String i : nom_vivants)
                tmp = String.format("%s%s, ",tmp,i);
        tmp = String.format("%s\n%s\n",tmp,"\nportes de la pièce: ");
        Set<String> nom_portes = this.portes.keySet();
        for(String i : nom_portes)
                tmp = String.format("%s%s, ",tmp,i);
        return tmp;
    }
    
}
