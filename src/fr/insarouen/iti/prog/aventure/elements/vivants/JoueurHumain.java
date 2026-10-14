package fr.insarouen.iti.prog.aventure.elements.vivants;

import java.util.Scanner;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.ActivationException;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;
import fr.insarouen.iti.prog.aventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteFermeException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;

import java.util.Set;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Classe JoueurHumain
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class JoueurHumain extends Vivant {

    private String ordre;

    // Constructeur

        /**
         * Constructeur de JoueurHumain à partir de son nom et de son monde
         * 
         * @param nom : chaine de caractères du nom de JoueurHumain
         * @param monde : monde de JoueurHumain
         * @param pointVie : point de vie du JoueurHumain
         * @param pointForce : point de force du JoueurHumain
         * @param piece :  piece dans laquelle se situe le JoueurHumain
         * @param tabObjet : les objets que  possède le JoueurHumain
         */
    public JoueurHumain(String nom, Monde monde, int pointsVie, int pointsForce, Piece piece, Objet... objets) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException {
	    super(nom, monde, pointsVie, pointsForce, piece, objets);
    }

    //Méthodes

    /**
     * Permet d'accédé aux ordres d’action de joueur humain
     * 
     * @return String
     */
    public String getOrdre() {
	    return this.ordre;
    }

    /**
     * Permet de Donner un ordre d’action à un joueur humain 
     * 
     * @param ordre : chaine de caractère contient les ordres  
     * 
     */
    public void setOrdre(String ordre) {
	    this.ordre = ordre;
    }

    /**
     * Permet prendre une Objet 
     * 
     * @param nomObjet: nom Objet à prendre 
     * 
     */
    private void commandePrendre(String nomObjet) throws ObjetNonDeplacableException, ObjetAbsentDeLaPieceException {
	    this.prendre(nomObjet);
    }

     /**
     * Permet poser une Objet 
     * 
     * @param nomObjet : nom Objet à déposer 
     * 
     */
    private void commandePoser(String nomObjet) throws ObjetNonPossedeParLeVivantException {
	    this.deposer(nomObjet);
    }

     /**
     * Permet de franchir une porte 
     * 
     * @param nomPorte  : nom de Porte a franchir 
     * 
     */
    private void commandeFranchir(String nomPorte) throws VivantAbsentDeLaPieceException, PorteFermeException, PorteInexistanteDansLaPieceException {
	    this.franchir(nomPorte);
    }

     /**
     * Permet d'ouvrir une porte 
     * 
     * @param nomPorte  : nom de Porte a ouvrir
     * 
     */
    private void commandeOuvrirPorte(String nomPorte) throws ActivationException, PorteInexistanteDansLaPieceException {
	    if (!this.getPiece().aLaPorte(nomPorte))
        throw new PorteInexistanteDansLaPieceException(
            String.format("%s ne possède pas la porte %s", this.getPiece().getNom(), nomPorte));
        this.getPiece().getPorte(nomPorte).activer();
    }

     /**
     * Permet d'ouvre la porte en utilisant l'objet
     * 
     * @param nomPorte  : nom de Porte a ouvrir
     * @param nomObjet : nom d'objet utlisé pour ouvrir la porte 
     * 
     */
    private void commandeOuvrirPorte(String nomPorte, String nomObjet) throws ActivationException, PorteInexistanteDansLaPieceException, ObjetNonPossedeParLeVivantException {
        Objet objet = this.getObjet(nomObjet);
        
        if (objet == null)
            throw new ObjetNonPossedeParLeVivantException(
                String.format("%s ne possède pas %s", this.getNom(), nomObjet));

        if (!this.getPiece().aLaPorte(nomPorte))
            throw new PorteInexistanteDansLaPieceException(
                String.format("%s ne possède pas la porte %s", this.getPiece().getNom(), nomPorte));
        this.getPiece().getPorte(nomPorte).activerAvec(objet);
    }

    /**
     * Demande à l’utilisateur de saisir un ordre pour le joueur et l’exécuter .
     * L’ordre doit commencer par le nom d’une commande, suivi des paramètres.
     * 
     */
    @Override
    public void executer() throws ITIAventureException {
        System.out.println(String.format("\n\nDonner un ordre pour le joueur %s", this.getNom()));
        System.out.println("Par exemple : \n"
                + "OuvrirPorte Porte666 avec cledudiable \n"
                + "Prendre cle1\n"
                + "Poser cle1\n"
                + "Franchir porte1\n"
                + "OuvrirPorte porte1\n");
        Scanner sc1 = new Scanner(System.in);
        
        while (sc1.hasNext()) {
            String ligne = sc1.nextLine();
            String[] mots = ligne.split(" ");
            String commande = String.format("commande%s", mots[0]);
            String[] motsFiltrer = Arrays.stream(mots)
                                        .filter(mot -> this.getMonde().getEntite(mot) != null)
                                        .toArray(String[]::new);

            Class<?>[] typesStringClass = new Class[motsFiltrer.length];
            Arrays.fill(typesStringClass, String.class);

            // Récupérer toutes les méthodes de la classe
            Method[] methodes = this.getClass().getDeclaredMethods();
            
            for (Method methode : methodes) {
                if (methode.getName().equals(commande)) {
                    Class<?>[] paramTypes = methode.getParameterTypes();
                    if (paramTypes.length == motsFiltrer.length) {
                        try {
                            methode.invoke(this, (Object[]) motsFiltrer);
                            return;  // on sort si la méthode à réussi
                        } catch (InvocationTargetException e) {
                            Throwable cause = e.getCause();
                            
                            if (cause instanceof ITIAventureException) {
                                throw new ITIAventureException("Erreur pendant l'exécution de la commande : " + cause.getMessage(), cause);
                            }
                            
                            throw new ITIAventureException("Erreur inconnue pendant l'exécution de la commande", e);
                        } catch (Exception e) {
                            throw new CommandeImpossiblePourLeVivantException("Impossible d'exécuter la commande " + commande + " : " + e.getMessage(), e);
                        }
                    }
                }
            }
            // Si aucune méthode ne correspond
            throw new CommandeImpossiblePourLeVivantException("Impossible d'exécuter la commande " + commande);
        }
    }
}