package fr.insarouen.iti.prog.aventure.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Scanner;

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.data.Enregistreur;
import fr.insarouen.iti.prog.aventure.data.EnregistreurSerialisation;
import fr.insarouen.iti.prog.aventure.data.Lecteur;
import fr.insarouen.iti.prog.aventure.data.LecteurDescription;
import fr.insarouen.iti.prog.aventure.data.LecteurSerialisation;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;


import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;

import fr.insarouen.iti.prog.aventure.elements.structure.Piece;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.lang.ClassNotFoundException;
import java.util.stream.Stream;


import java.io.ObjectOutputStream;
import java.io.IOException;
import fr.insarouen.iti.prog.aventure.Monde;
import java.util.Collection;

import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;

public class MainTest {

    private static Enregistreur enregistreur=null;

    private static Monde monde=null;
    private static Collection<ConditionDeFin> conditionsDeFin=null;

    
    public static void sauvegarderFichierSerialisation() {
	    try {
            System.out.println("Test 1");

            Connection connection = DriverManager.getConnection("jdbc:postgresql://iti-pg.insa-rouen.fr:5432/grtt1","grtt1", "grtt1");
            enregistreur = new EnregistreurBD(connection);
            Monde monde = new Monde("Mondeprout");
            Piece piece = new Piece("prout de la piece", monde);
            Piece piece2 = new Piece("Piece2", monde);

            PiedDeBiche pdb = new PiedDeBiche("PDBNOMMMM", monde);
            PiedDeBiche pdb2 = new PiedDeBiche("BEHEEHEHEEEE", monde);
            System.out.println("Test 1");
            JoueurHumain jhA = new JoueurHumain("ambro", monde, 1, 1, piece, pdb2);
            System.out.println("Test 2");
            JoueurHumain jhS = new JoueurHumain("Selmaaaa", monde, 100, 57612, piece, pdb);
            System.out.println("Test 3");
            JoueurHumain jh1 = new JoueurHumain("Ambrogato", monde, 12, 1, piece2);
            System.out.println("Test 4");

            Porte porte = new Porte("Portenom", monde, piece, piece2);

            Collection<ConditionDeFin> conditionsDeFin=null;
            enregistreur.enregistrer(monde, conditionsDeFin);
	    } catch (Throwable e) {
            enregistreur = null;
            System.err.println("La sauvegarde a rencontré un problème");
            System.err.println(String.format("---> %s ",e.getMessage()));
            e.printStackTrace();
	    }
	}
    

    public static void main(String[] args) throws ITIAventureException{
        sauvegarderFichierSerialisation();
    }
}