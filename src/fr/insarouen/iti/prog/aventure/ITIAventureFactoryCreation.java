package fr.insarouen.iti.prog.aventure;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.vivants.Monstre;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure; 
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;



// jsp si faut faire les exceptions aussi 

public class ITIAventureFactoryCreation extends ITIAventureFactory{
    public ITIAventureFactoryCreation(String nom){
        super(nom);
    }
    
    @Override
    public Monde creerMonde(String nom){
        return new Monde(nom);
    }

    @Override
    public Piece creerPiece(Monde monde, String nom)throws NomDEntiteDejaUtiliseDansLeMondeException{
        Piece piece = new Piece(nom, monde);
        
        return piece;
        
    }

    @Override
    public Porte creerPorte(Monde monde, String nom, Piece p1, Piece p2, Serrure serrure)throws NomDEntiteDejaUtiliseDansLeMondeException{
        if (serrure==null){
            Porte porte = new Porte(nom, monde,p1, p2);
            return porte;
        }
        return new Porte(nom, monde, serrure, p1, p2);
    }

    @Override
    public Objet creerPiedDeBiche(Monde monde, String nom)throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException{
        
            PiedDeBiche pdb = new PiedDeBiche(nom, monde);
            return pdb;
        
    }

    @Override
    public Objet creerCle(Serrure serrure)throws NomDEntiteDejaUtiliseDansLeMondeException{
        Cle cle = serrure.creerCle();
        return cle;
    }

    @Override
    public JoueurHumain creerHumain(Monde monde, String nom, int pv, int force, Piece position) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException{
        return new JoueurHumain(nom, monde, pv, force, position);
    }


    @Override
    public Serrure creerSerrure(Monde monde)throws NomDEntiteDejaUtiliseDansLeMondeException{
        Serrure serrure = new Serrure(monde);
        return serrure;

    }

    @Override
    public Monstre creerMonstre(Monde monde, String nom, int pv, int force, Piece position) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException{
        return new Monstre(nom, monde, pv, force, position);
    }
}