package fr.insarouen.iti.prog.aventure.data;

import java.util.Scanner;
import java.io.Reader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.util.stream.Collectors;

import fr.insarouen.iti.prog.aventure.data.compil.ast.Affectation;
import fr.insarouen.iti.prog.aventure.data.compil.ast.AppelFonction;
import fr.insarouen.iti.prog.aventure.data.compil.ast.Argument;
import fr.insarouen.iti.prog.aventure.data.compil.ast.Chaine;
import fr.insarouen.iti.prog.aventure.data.compil.ast.Entier;
import fr.insarouen.iti.prog.aventure.data.compil.ast.EtatJeu;
import fr.insarouen.iti.prog.aventure.data.compil.ast.Identifiant;
import fr.insarouen.iti.prog.aventure.data.compil.ast.Configuration;

import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visitable;
import fr.insarouen.iti.prog.aventure.data.compil.visiteur.Visiteur;


import fr.insarouen.iti.prog.aventure.data.compil.analyseurs.AnalyseurSyntaxique;
import fr.insarouen.iti.prog.aventure.data.compil.analyseurs.ParseException;

import fr.insarouen.iti.prog.aventure.data.compil.erreursSemantique.SemantiqueErreurException;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.ITIAventureFactory;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.vivants.Monstre;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantDansPiece;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantPossedeObjets;

public class LecteurCompil implements Visiteur,Lecteur {

    private Map<String, Object> entites;
    private Monde monde;
    private Collection<ConditionDeFin> cdf;

    public LecteurCompil(Reader reader)  throws ITIAventureException, IOException, ArgumentException, ParseException, Throwable{
        this.cdf = new ArrayList<>();
        this.entites = new HashMap<>();
        AnalyseurSyntaxique analyseur = new AnalyseurSyntaxique(reader); 
        Configuration config = analyseur.config();
        config.accepter(this);
        System.out.println("\n=== Variables créées ===");
        for (Map.Entry<String, Object> entry : this.entites.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue().toString());
        }
        System.out.println("\n=== Conditions de fin ===");
        for (ConditionDeFin condition : this.cdf) {
            System.out.println(condition.toString());
        }
    }

    public void visiter(Configuration config) throws Throwable {
        for (Affectation affectation : config.getAffectations()) {
            affectation.accepter(this);
        }
    }

    public void visiter(Affectation affectation) throws Throwable {
        String nomVariable = affectation.getNomVar();
        if (this.entites.containsKey(nomVariable)) {
            throw new VariableDejaCreeException("La variable " + nomVariable + " est déjà définie :( ");
        }
        AppelFonction appel = affectation.getAppelFonction();
        appel.accepter(this);       
        Object entite = visiter(appel);  // résout d’abord les identifiants
             // vérifie ensuite les types
        this.entites.put(nomVariable, entite);
    }


    public Object visiter(AppelFonction appel) throws Throwable,VariablePasCreeException, SemantiqueErreurException, NomDEntiteDejaUtiliseDansLeMondeException,VivantAbsentDeLaPieceException {
        List<Object> arguments = new ArrayList<>();
        for (Argument arg : appel.getArguments()) {
            if (arg instanceof AppelFonction) {
                ((AppelFonction) arg).accepter(this);
                arguments.add(visiter((AppelFonction) arg));
            } 
            else if (arg instanceof Identifiant) {
                Object var= this.entites.get(((Identifiant)arg).getNom());
                if (var == null) {
                    throw new VariablePasCreeException("La variable " + ((Identifiant) arg).getNom() + " n'est pas définie, elle ne peux pas etre en parametre");
                }
                arguments.add(var);
            } 
            else if (arg instanceof EtatJeu) {
                arguments.add(EtatDuJeu.valueOf(((EtatJeu) arg).getEtat()));
            } 
            else if (arg instanceof Chaine) {
                arguments.add(((Chaine) arg).getChaine());
            } 
            else if (arg instanceof Entier) {
                arguments.add(((Entier) arg).getValeur());
            } 
        }
        return creeVariable(appel, arguments);
    }

    private Object creeVariable(AppelFonction appel, List<Object> arguments) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException{
        switch (appel.getNomFonction()){
            case monde:
                return this.creeMonde((String)arguments.get(0));
            case piece :
                return this.creePiece((Monde) arguments.get(0), (String)arguments.get(1));
            case porte:
                if (arguments.size() == 4){
                    this.creePorte((Monde) arguments.get(0), (String)arguments.get(1),
                        (Piece)arguments.get(2), (Piece)arguments.get(3));
                }
                else {
                    return this.creePorteSerrure((Monde) arguments.get(0), (String)arguments.get(1),
                        (Serrure)arguments.get(4), (Piece)arguments.get(2), (Piece)arguments.get(3));
                }
            case serrure:
                return this.creerSerrure((Monde) arguments.get(0));
            case cle :
                return this.creeCle((Porte) arguments.get(0), (Piece) arguments.get(1));      
            case pied_de_biche:
                return this.creePdb((Monde) arguments.get(0), (String) arguments.get(1));
            case humain:
                List<Objet> objetsJH = new ArrayList<>();
                for (int i = 5; i < arguments.size(); i++) {
                    objetsJH.add((Objet) arguments.get(i));
                }
                return this.creeJH((Monde) arguments.get(0), (String)arguments.get(1),
                        (int)arguments.get(2), (int)arguments.get(3), (Piece)arguments.get(4), objetsJH);
            case monstre:
                return this.creeMonstre((Monde) arguments.get(0), (String)arguments.get(1),
                        (int)arguments.get(2), (int)arguments.get(3), (Piece)arguments.get(4));
            case cdf_vivant_dans_piece :
                return this.creeCDFvivantPiece((EtatDuJeu) arguments.get(0), (Vivant)arguments.get(1),
                        (Piece)arguments.get(2));
            case cdf_vivant_possede:
                return this.creeConditionDeFinVivantPossedeObjets((EtatDuJeu) arguments.get(0), (Vivant)arguments.get(1),
                        (Objet)arguments.get(2));
            /*case cdf_conjonction:
                List<ConditionDeFin> cdfs = new ArrayList<>();
                for (int i = 0; i < arguments.size(); i++) {
                    cdfs.add((ConditionDeFin) arguments.get(i));
                }
                return this.creeConditionConjonction(cdfs);*/
            default:
                return null;
        }
    } 

    private Object creeMonde(String nom){
        this.monde =new Monde(nom);
        return this.monde;
    }

    private Object creePiece(Monde monde, String nom) throws NomDEntiteDejaUtiliseDansLeMondeException{
        return new Piece(nom, monde);
    }
    
    private Serrure creerSerrure(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        return new Serrure(monde);
    }

    private Object creePorte(Monde monde, String nom, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException{
        return new Porte(nom, monde, pieceA, pieceB);
    }

    private Object creePorteSerrure(Monde monde, String nom, Serrure serrure, Piece pieceA, Piece pieceB) throws NomDEntiteDejaUtiliseDansLeMondeException{
        return new Porte(nom, monde, serrure, pieceA, pieceB );
    }  
    
    private Object creeCle(Porte porte, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException{
        Cle cle = porte.getSerrure().creerCle();
        piece.deposer(cle);
        return cle;
    }

    private Object creePdb(Monde monde, String nom) throws NomDEntiteDejaUtiliseDansLeMondeException{
        return new PiedDeBiche(nom, monde);
    }

    private Object creeJH(Monde monde, String nom, int pointsVie, int pointsForce, Piece piece, List<Objet> objets) throws NomDEntiteDejaUtiliseDansLeMondeException , VivantAbsentDeLaPieceException{
        return new JoueurHumain(nom, monde, pointsVie, pointsForce, piece, objets.toArray(new Objet[0]));  
    }

    private Object creeMonstre(Monde monde, String nom, int pointsVie, int pointsForce, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException , VivantAbsentDeLaPieceException{
        return new Monstre(nom, monde, pointsVie, pointsForce, piece);
    }
    private Object creeCDFvivantPiece(EtatDuJeu etat, Vivant vivant, Piece piece) {
        ConditionDeFinVivantDansPiece cdf =new ConditionDeFinVivantDansPiece(etat, vivant, piece);
        this.cdf.add(cdf);
        return cdf;
    }

    private Object creeConditionDeFinVivantPossedeObjets(EtatDuJeu etat, Vivant vivant, Objet objet) {
        ConditionDeFinVivantPossedeObjets cdf =new ConditionDeFinVivantPossedeObjets(etat, vivant, objet);
        this.cdf.add(cdf);
        return cdf;
    }

    /*private Object creeConditionConjonction(List<ConditionDeFin> cdfs) {
        ConditionDeFinVivantPossedeObjets cdf =new Con(etat, vivant, objets.toArray(new Objet[0]));
        this.cdf.add(cdf);
        return cdf;
    }*/


    public Monde getMonde() throws ITIAventureException {
        if (this.monde == null){
            throw new ITIAventureException("monde vide");
        }
        return this.monde;
    }

    public Collection<ConditionDeFin> getConditionsDeFin() {
        return this.cdf;
    }

}