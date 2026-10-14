package fr.insarouen.iti.prog.aventure;

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
import fr.insarouen.iti.prog.aventure.data.EnregistreurBD;
import fr.insarouen.iti.prog.aventure.data.Lecteur;
import fr.insarouen.iti.prog.aventure.data.LecteurDescription;
import fr.insarouen.iti.prog.aventure.data.LecteurSerialisation;
import fr.insarouen.iti.prog.aventure.data.LecteurBD;
import fr.insarouen.iti.prog.aventure.data.LecteurCompil;

import java.sql.DriverManager;
import java.sql.Connection;

public class Main {
    private static String nomFichier;
    private static Lecteur lecteur=null;
    private static Enregistreur enregistreur=null;
    private static Simulateur simulateur=null;
    private static Monde monde=null;
    private static Collection<ConditionDeFin> conditionsDeFin=null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Throwable{
	while (true) {
	    System.out.println();
	    System.out.println();
	    System.out.println("0/ jouer un tour");
	    System.out.println("1/ jouer jusqu'à la fin sans possibilité d'arrêter");
	    System.out.println("2/ charger fichier de description");
	    System.out.println("3/ sauvegarder une partie (serialisation)");
	    System.out.println("4/ charger une sauvegarde (serialisation)");
		System.out.println("5/ sauvegarder une partie(BD)");
		System.out.println("6/ charger une sauvegarde(BD)");
	    System.out.println("7/ charger fichier de compil");
	    System.out.println("8/ quitter");

	    try {
		switch (scanner.nextInt()) {
		case 0:
		    if (simulateur == null) {
			System.out.println("Vous n'avez pas de partie en cours");
		    } else {
			simulateur.executerUnTour();
		    }
		    break;
		case 1:
		    if (simulateur == null) {
			System.out.println("Vous n'avez pas de partie en cours");
		    } else {
			simulateur.executerJusquALaFin();
		    }
		    break;
		case 2:
		    chargerFichierDescription();
			break;
		case 3:
		    sauvegarderFichierSerialisation();
		    break;
		case 4 : 
			chargerFichierSerialisation();
			break;
		case 5:
			sauvegarderBD();
			break;
		case 6:
		    chargerBD();
		    break;
		case 7:
		    chargerCompil();
		    break;
		case 8:
		    scanner.close();
		    System.exit(1);
		    break;
		default:
		    System.err.println("Choisissez une valeur entre 1 et 6.");
		}
	    } catch (java.util.InputMismatchException e) {
		System.out.println("Saisie incorrecte");
		scanner.nextLine();
	    }
	}
    }

    public static void chargerFichierDescription() {
	System.out.println("Chargement d'un fichier textuel de description");
	System.out.print("Veuillez saisir le nom du fichier : ");
	nomFichier = scanner.next();
	try {
	    lecteur = new LecteurDescription(new FileReader(nomFichier));
	    monde = lecteur.getMonde();
	    conditionsDeFin = lecteur.getConditionsDeFin();
	    simulateur = new Simulateur(monde, conditionsDeFin);
	} catch (Throwable e) {
	    lecteur = null;
	    System.err.println("La lecture de votre fichier a rencontré un problème");
	    System.err.println(String.format("---> %s ",e.getMessage()));
	}
    }

    public static void sauvegarderFichierSerialisation() {
	System.out.println("Sauvegarde de la partie en cours");
	if (monde == null) {
	    System.out.println("Vous n'avez pas de partie en cours");
	} else {
	    System.out.print("Veuillez saisir le nom du fichier : ");
	    nomFichier = scanner.next();
	    try {
		enregistreur = new EnregistreurSerialisation(new ObjectOutputStream(new FileOutputStream(nomFichier)));
		enregistreur.enregistrer(monde,conditionsDeFin);
	    } catch (Throwable e) {
		enregistreur = null;
		System.err.println("La sauvegarde a rencontré un problème");
		System.err.println(String.format("---> %s ",e.getMessage()));
	    }
	}
    }

    public static void chargerFichierSerialisation() throws Throwable{
		System.out.println("Chargement d'une sauvegarde");
		System.out.print("Veuillez saisir le nom du fichier : ");
		nomFichier = scanner.next();
		try {
			lecteur = new LecteurSerialisation(new ObjectInputStream(new FileInputStream(nomFichier)));
			monde = lecteur.getMonde();
			conditionsDeFin = lecteur.getConditionsDeFin();
			simulateur = new Simulateur(monde, conditionsDeFin);
		} catch (Exception e) {
			lecteur = null;
			System.err.println("La lecture de votre fichier a rencontré un problème");
			System.err.println(e.getMessage());
		}
    }

	public static void chargerBD() throws Throwable{
		System.out.println("On charge depuis la base donné le monde !");
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://iti-pg.insa-rouen.fr:5432/grtt1","grtt1", "grtt1");
			lecteur = new LecteurBD(connection);
			monde = lecteur.getMonde();
			//conditionsDeFin = lecteur.getConditionsDeFin();
			simulateur = new Simulateur(monde, null);
		} catch (Exception e) {
			lecteur = null;
			System.err.println("La lecture de votre fichier a rencontré un problème");
			System.err.println(e.getMessage());
		}

	}

	public static void sauvegarderBD() throws Throwable {
		System.out.println("Sauvegarde de la partie en cours");
			if (monde == null) {
				System.out.println("Vous n'avez pas de partie en cours");
			} else {
				try {
				Connection connection = DriverManager.getConnection("jdbc:postgresql://iti-pg.insa-rouen.fr:5432/grtt1","grtt1", "grtt1");
				enregistreur = new EnregistreurBD(connection);
				enregistreur.enregistrer(monde,conditionsDeFin);
				} catch (Throwable e) {
				enregistreur = null;
				System.err.println("La sauvegarde a rencontré un problème");
				System.err.println(String.format("---> %s ",e.getMessage()));
				}
			}
	}
			
	public static void chargerCompil() {
		System.out.println("Chargement d'un fichier textuel ");
		System.out.print("Veuillez saisir le nom du fichier : ");
		nomFichier = scanner.next();
		try {
			lecteur = new LecteurCompil(new FileReader(nomFichier));
			monde = lecteur.getMonde();
			conditionsDeFin = lecteur.getConditionsDeFin();
			simulateur = new Simulateur(monde, conditionsDeFin);
		} catch (Throwable e) {
			lecteur = null;
			System.err.println("La lecture de votre fichier a rencontré un problème");
			System.err.println(String.format("---> %s ",e.getMessage()));
		}
    }



}
