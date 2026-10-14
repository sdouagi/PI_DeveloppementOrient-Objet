package fr.insarouen.iti.prog.aventure.data;

import java.util.Scanner;
import java.io.Reader;
import java.io.IOException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.data.Lecteur; 
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantDansPiece;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;
import fr.insarouen.iti.prog.aventure.ITIAventureFactory;
import fr.insarouen.iti.prog.aventure.ITIAventureFactoryCreation;
import fr.insarouen.iti.prog.aventure.spaceopera.ITISpaceOperaFactory;


import java.util.Collection;
import java.util.ArrayList;
/**
 * Classe LecteurDescription
 * 
 * Cette classe permet de lire un flux contenant une description du jeu d’aventure
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class LecteurDescription implements Lecteur{

    private final Scanner scanner;
    private final Collection<ConditionDeFin> CDFV = new ArrayList<>();
    private final ITIAventureFactory factory;//aj
    private Monde monde;


     /**
     * Constructeur de LecteurDescription
     * 
     * @param reader : flux d'entrée contenant une description du jeu d’aventure
     * 
     * 
     */
    public LecteurDescription(Reader reader){
        this.scanner=new Scanner(reader);
        String nomFactory= scanner.next();
        switch (nomFactory) {
            case "ITISpaceOpera":
                this.factory = new ITISpaceOperaFactory(nomFactory);
                break;
            case "ITIAventure":
                this.factory = new ITIAventureFactoryCreation(nomFactory);
                break;
            default:
                throw new IllegalArgumentException("Factory inconnue : " + nomFactory);
        }
        //this.factory=new ITIAventureFactoryCreation(nomFactory);//avant
        
    } 
    
    @Override
    public Monde getMonde()throws ITIAventureException{
        while (scanner.hasNext()){
            String motAct = scanner.next();
            switch(motAct){
                case "Monde":
                    methodeMonde(scanner.next());
                    break;
                case "Piece":
                    methodePiece(scanner.next());
                    break;
                case "PorteSerrure":
                    methodePorteSerrure(scanner);
                    break;
                case "Porte":
                    methodePorte(scanner);
                    break;
                case "Cle":
                    methodeCle(scanner);
                    break;
                case "JoueurHumain":
                    methodeJoueurHumain(scanner); 
                    break;
                case "ConditionDeFinVivantDansPiece":
                    methodeCDFVDP(scanner);
            }
        }
        return monde;
    }

    
    //Méthodes

    /**
     * Création d'un monde
     * 
     * @param nomMonde : nom du monde a crée
     * 
     */
    private void methodeMonde(String nomMonde) throws ITIAventureException{
        this.monde= factory.creerMonde(nomMonde);
    }

    /**
     * Création d'une pièce
     * 
     * @param nomPiece : nom de la pièce
     *  
     */
    private void methodePiece(String nomPiece)  throws ITIAventureException{
        factory.creerPiece(monde, nomPiece);
    }

     /**
     * Création d'une porte avec serrure entre deux pièces
     * 
     * @param sc : scanner contenant la description du jeu en particulier les informations concernant la serrure du porte 
     * 
     */
    private void methodePorteSerrure(Scanner sc) throws ITIAventureException {
        String nomPorte = sc.next();
        String nomPiece1 = sc.next();
        String nomPiece2 = sc.next();
        Piece piece1 = (Piece) monde.getEntite(nomPiece1);
        if ( piece1 == null) {
            piece1=  factory.creerPiece(this.monde, nomPiece1);
        }
        Piece piece2 = (Piece) monde.getEntite(nomPiece2);
        if ( piece2 == null) {
            piece2=  factory.creerPiece(this.monde, nomPiece2);
        }
        Serrure serrure = factory.creerSerrure(monde);
        factory.creerPorte(this.monde, nomPorte , piece1, piece2 , serrure);
    }

     /**
     * Création d'une porte entre deux pièces
     * 
     * @param sc : scanner contenant la description du jeu en particulier les informations concernant la porte
     * 
     */
    private void methodePorte(Scanner sc) throws ITIAventureException {
        String nomPorte = sc.next();
        String nomPiece1 = sc.next();
        String nomPiece2 = sc.next();
        Piece piece1 = (Piece) monde.getEntite(nomPiece1);
        if ( piece1 == null) {
            piece1=  factory.creerPiece(this.monde, nomPiece1);
        }
        Piece piece2 = (Piece) monde.getEntite(nomPiece2);
        if ( piece2 == null) {
            piece2=  factory.creerPiece(this.monde, nomPiece2);
        }
        factory.creerPorte(monde, nomPorte, piece1, piece2, null);
    }

    /**
     * Création d'une clé associée à une porte
     * 
     * @param sc : scanner contenant la description du jeu en particulier les informations concernant le clé
     * 
     */
    private void methodeCle(Scanner sc) throws ITIAventureException {
        String nomPorte = sc.next();
        String nomPiece = sc.next();
        Porte porte = (Porte)this.monde.getEntite(nomPorte);
        Serrure serrure = porte.getSerrure();
        Cle cle = (Cle)factory.creerCle(serrure);
        Piece piece = (Piece)this.monde.getEntite(nomPiece);
        piece.deposer(cle);
    }

    /**
     * Création d'un joueur humain
     * 
     * @param sc : scanner contenant la description du jeu en particulier les informations du joueur
     * 
     */
    private void methodeJoueurHumain(Scanner sc) throws ITIAventureException{
        String nomJH = sc.next();
        int pointV = sc.nextInt();
        int pointF = sc.nextInt();
        String nomPiece = sc.next();
        Piece piece = (Piece) monde.getEntite(nomPiece);
        if ( piece == null) {
            piece= factory.creerPiece(this.monde, nomPiece);
        }
        factory.creerHumain(this.monde,nomJH,  pointV, pointF, piece);
    }

    private void methodeCDFVDP(Scanner sc) throws ITIAventureException{
        EtatDuJeu etat = EtatDuJeu.valueOf(sc.next());
        String nomJH = sc.next();
        String nomPiece = sc.next();
        Piece piece = (Piece) monde.getEntite(nomPiece);
        
        JoueurHumain jh = (JoueurHumain) monde.getEntite(nomJH);
        this.CDFV.add(new ConditionDeFinVivantDansPiece(etat, jh, piece));
    }
    
        

    @Override
    public Collection<ConditionDeFin> getConditionsDeFin(){
        return this.CDFV;
    }

}
