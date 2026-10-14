package fr.insarouen.iti.prog.aventure.spaceopera;

import fr.insarouen.iti.prog.aventure.ITIAventureFactory;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.vivants.Monstre;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;

public class ITISpaceOperaFactory extends ITIAventureFactory {

    public ITISpaceOperaFactory(String nom) {
        super(nom);
    }

    @Override
    public Monde creerMonde(String nom) {
        return new Galaxie(nom);  
    }

    @Override
    public Piece creerPiece(Monde monde, String nom) throws NomDEntiteDejaUtiliseDansLeMondeException {
        return new VaisseauSpatial(nom, monde); 
    }

    @Override
    public Porte creerPorte(Monde monde, String nom, Piece p1, Piece p2, Serrure serrure) throws NomDEntiteDejaUtiliseDansLeMondeException {
        if (serrure==null){
            Teleporteur teleporteur= new Teleporteur(nom, monde,p1, p2);
            return teleporteur;
        }
        return new Teleporteur(nom, monde, serrure, p1, p2);
    }

    @Override
    public Objet creerPiedDeBiche(Monde monde, String nom) throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException {
        return new PiedDeBiche(nom, monde);
    }

    @Override
    public Objet creerCle(Serrure serrure) throws NomDEntiteDejaUtiliseDansLeMondeException {
        return serrure.creerCle();
        
    }

    @Override
    public Serrure creerSerrure(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        return new LecteurBadge(monde); 
    }

    @Override
    public Monstre creerMonstre(Monde monde, String nom, int pv, int force, Piece position) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException {
        return new Alien(nom, monde, pv, force, position);
    }

    @Override
    public JoueurHumain creerHumain(Monde monde, String nom, int pv, int force, Piece position) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException {
        return new JoueurHumain(nom, monde, pv, force, position); 
    }
}
