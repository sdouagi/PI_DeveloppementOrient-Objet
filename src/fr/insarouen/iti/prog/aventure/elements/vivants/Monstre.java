package fr.insarouen.iti.prog.aventure.elements.vivants;

import java.util.List;
import java.util.stream.Collectors;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.Executable;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;


import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteFermeException;
import fr.insarouen.iti.prog.aventure.elements.vivants.ObjetNonPossedeParLeVivantException;
import fr.insarouen.iti.prog.aventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleException;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;

/**
 * Classe Vivant
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class Monstre extends Vivant implements Executable {

    // Constructeur

    /**
     * Constructeur de Monstre à partir de son nom et de son monde
     * 
     * @param nom : chaine de caractères du nom de l'entité
     * @param monde : monde de l'entité
     * @param pointVie : point de vie du Monstre
     * @param pointForce : point de force du Monstre
     * @param piece :  piece dans laquelle se situe le Monstre
     */
    public Monstre(String nom, Monde monde, int pointsVie, int pointsForce, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException , VivantAbsentDeLaPieceException{
        super(nom, monde, pointsVie, pointsForce, piece);
    }
    
    public void executer() throws VivantAbsentDeLaPieceException, PorteFermeException, PorteInexistanteDansLaPieceException, ObjetNonPossedeParLeVivantException, ObjetAbsentDeLaPieceException, ActivationImpossibleException {
        
        if (!(this.estMort())) { 

            this.setPointVie(this.getPointVie() - 1);

            if (!(this.estMort())) { 

                List<Porte> portes = this.getPiece().getPortes().stream().filter(p->(p.getEtat()!=Etat.VERROUILLE)).collect(Collectors.toList());
                if (!portes.isEmpty()) {
                    Porte porte = portes.get((int) (Math.random() * portes.size()));
                    if (porte.getEtat() == Etat.FERME) {
                        porte.activer();
                    }
                    this.franchir(porte);
                    List<String> objetsADeposer = this.getObjets().stream().map(Objet::getNom) .collect(Collectors.toList());
                    List<Objet> objetsAPrendre = this.getPiece().getObjets().stream().collect(Collectors.toList());
                    for (String nom : objetsADeposer) {
                        if (this.possede(nom)) {
                            this.deposer(nom);
                        }
                    }
                    for (Objet obj : objetsAPrendre) {
                        try {
                            this.prendre(obj);
                        } catch(ObjetNonDeplacableException e) {
                        //On fait rien :)
                        }
                    }
                }
            }
        }
    }
}