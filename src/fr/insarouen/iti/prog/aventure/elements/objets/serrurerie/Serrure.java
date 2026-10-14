package fr.insarouen.iti.prog.aventure.elements.objets.serrurerie;


import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleAvecObjetException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleException;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.Activable;

/**
 * Classe Objet
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Serrure extends Objet implements Activable{

    private Etat etat;
    protected String nomCle = "" ;

    // Constructeur
    /**
     * Constructeur de Objet à partir de son monde
     * 
     * @param monde : monde de l'entité
     */
    public Serrure(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(monde.generateNom("serrure"), monde);
        this.etat = Etat.VERROUILLE;
    }

    /**
     * Constructeur de Objet à partir de son monde
     * 
     * @param monde : monde de l'entité
     */
    public Serrure( String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
        super(nom, monde);
        this.etat = Etat.VERROUILLE;
    }


    @Override
    /**
     * Constructeur de Objet à partir de son nom et de son monde
     * 
     * @param monde : monde de l'entité
     * @param nom : nom de l'entité
     * 
     * @return booléen
     */
    public boolean estDeplacable(){
        return false;
    }

    /**
     * Permet d'obtenir l'etat de la porte 
     * 
     * @return Etat
     */
    public Etat getEtat(){
        return this.etat;
    }
                                               
    @Override
    /**
     * Permet de changer l'etat de la porte 
     * @param Objet : Objet pour activé une porte
     */
    public void activerAvec(Objet obj) throws ActivationImpossibleAvecObjetException {
        if (!activableAvec(obj)){
            throw new ActivationImpossibleAvecObjetException(" ce n'est pas la bonne clé");
        }
        else{
            this.etat= switch(this.etat) {
                case Etat.DEVERROUILLE -> Etat.VERROUILLE;
                case Etat.VERROUILLE -> Etat.DEVERROUILLE;
                default -> Etat.DEVERROUILLE;
            };
        }    
    }

    @Override
    /**
     * Permet d'activé la porte avec un Objet
     *  
     */
    public void activer() throws ActivationImpossibleException{
        throw new ActivationImpossibleException("impossible d'activer la porte sans une cle");
    }

    @Override
    /**
     * Permet de savoir si l'objet active la porte
     * 
     * @param Objet : Objet pour activé une porte
     */
    public boolean activableAvec(Objet objet){
        return this.nomCle == objet.getNom();
    }   

    /**
     * Crée une clé si elle n'existe pas encore.
     * 
     * @return la clé créée, ou null si elle existe déjà.
     * 
     */
    public Cle creerCle() throws NomDEntiteDejaUtiliseDansLeMondeException{
        if (nomCle==""){
            Cle cle = new Cle(this.getMonde());
            this.nomCle = cle.getNom();
            return cle;
        }
        else {
            return null;
        }

    }




}
