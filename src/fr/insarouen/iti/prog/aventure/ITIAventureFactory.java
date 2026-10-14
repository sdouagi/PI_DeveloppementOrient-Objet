package fr.insarouen.iti.prog.aventure;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.vivants.Monstre;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure; 
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;


public abstract class ITIAventureFactory {
    protected String nom;

    public ITIAventureFactory(String nom){
        this.nom=nom;
    }
    public String getNom(){
        return nom;
    }
    public abstract Monde creerMonde(String nom);
    public abstract Piece creerPiece(Monde monde, String nom)throws NomDEntiteDejaUtiliseDansLeMondeException;
    public abstract Porte creerPorte(Monde monde, String nom, Piece p1, Piece p2, Serrure serrure)throws NomDEntiteDejaUtiliseDansLeMondeException;
    public abstract Objet creerPiedDeBiche(Monde monde, String nom)throws NomDEntiteDejaUtiliseDansLeMondeException,EntiteDejaDansUnAutreMondeException;
    public abstract Objet creerCle(Serrure serrure)throws NomDEntiteDejaUtiliseDansLeMondeException;
    public abstract Serrure creerSerrure(Monde monde)throws NomDEntiteDejaUtiliseDansLeMondeException ;
    public abstract Monstre creerMonstre(Monde monde, String nom, int pv, int force, Piece position)throws NomDEntiteDejaUtiliseDansLeMondeException,VivantAbsentDeLaPieceException;
    public abstract JoueurHumain creerHumain(Monde monde, String nom, int pv, int force, Piece position )throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException;
    
}