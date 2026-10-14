package fr.insarouen.iti.prog.aventure.data;

import fr.insarouen.iti.prog.aventure.elements.*;
import fr.insarouen.iti.prog.aventure.elements.structure.*;
import fr.insarouen.iti.prog.aventure.elements.vivants.*;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.*;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.conditions.*;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;




import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TestFactory {

    /* ====== DONNÉES ====== */
    private static Monde mondeActuel = null;
    private static Collection<ConditionDeFin> conditions = new ArrayList<>();
    private static final Scanner clavier = new Scanner(System.in);

    /* ====== MAIN ====== */
    public static void main(String[] args) {
        boolean continuer = true;
        while (continuer) {
            menuPrincipal();
            switch (lireInt()) {
                case 1 -> lancerJeu();
                case 2 -> chargerDescription();
                case 3 -> sauvegarderPartie();
                case 4 -> chargerPartie();
                case 5 -> { continuer = false; System.out.println("Au revoir !"); }
                default -> System.out.println("Choix invalide.");
            }
        }
        clavier.close();
    }

    /* ====== MENUS ====== */
    private static void menuPrincipal() {
        System.out.println("\n=== MENU ===");
        System.out.println("1/ Jouer");
        System.out.println("2/ Charger un fichier .aa");
        System.out.println("3/ Sauvegarder la partie");
        System.out.println("4/ Charger une partie sérialisée");
        System.out.println("5/ Quitter");
        System.out.print("Choix : ");
    }
    private static void menuJeu() {
        System.out.println("\n--- JEU ---");
        System.out.println("1/ Afficher état");
        System.out.println("2/ Déplacer un joueur");
        //System.out.println("3/ Vérifier conditions");
        System.out.println("4/ Retour menu principal");
        System.out.print("Choix : ");
    }

    /* ====== CHARGER LE FICHIER .AA ====== */
    private static void chargerDescription() {
        System.out.print("Nom du fichier .aa : ");
        String nomFichier = clavier.nextLine().trim();
        try (FileReader fr = new FileReader(nomFichier)) {
            LecteurDescription lec = new LecteurDescription(fr);
            mondeActuel = lec.getMonde();

            // On initialise la liste vide
            conditions = new ArrayList<>();
            // On parcourt toutes les conditions et on ne garde que celles du bon type
            for (ConditionDeFin c : lec.getConditionsDeFin()) {
                if (c instanceof ConditionDeFinVivantDansPiece) {
                    conditions.add((ConditionDeFinVivantDansPiece) c);
                }
            }

            System.out.println("Monde chargé : " + mondeActuel.getNom());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    /* ====== SAUVEGARDE / RECHARGEMENT ====== */
    private static void sauvegarderPartie() {
        if (mondeActuel == null) { System.out.println("Aucune partie."); return; }
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("partie.ser"))) {
            new EnregistreurSerialisation(o).enregistrer(mondeActuel,conditions);
            System.out.println(" Sauvegardé (partie.ser)");
        } catch (IOException e) { System.out.println(" Erreur sauvegarde : " + e.getMessage()); }
    }
    private static void chargerPartie() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("partie.ser"))) {
            mondeActuel = new LecteurSerialisation(in).getMonde();
            conditions = new ArrayList<>(); // conditions non sérialisées ici
            System.out.println(" Partie rechargée.");
        } catch (Exception e) { System.out.println(" Erreur chargement : " + e.getMessage()); }
    }

    /* ====== MODE JEU ====== */
    private static void lancerJeu() {
        if (mondeActuel == null) { System.out.println("Chargez un monde d’abord."); return; }
        boolean continuer = true;
        while (continuer) {
            menuJeu();
            switch (lireInt()) {
                case 1 -> afficherEtat();
                case 2 -> deplacerJoueur();
                //case 3 -> verifierConditions();
                case 4 -> continuer = false;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /* ====== OUTILS ====== */
    private static int lireInt() {
        try { return Integer.parseInt(clavier.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    /* -- liste des pièces sans changer Monde -- */
    private static List<Piece> getPieces() {
        return mondeActuel.getEntites().stream()
                .filter(e -> e instanceof Piece)
                .map(e -> (Piece) e)
                .collect(Collectors.toList());
    }

    private static void afficherEtat() {
        for (Piece p : getPieces()) {
            System.out.print("[ " + p.getNom() + " ] ");
            if (p.getVivants().isEmpty()) System.out.println("(vide)");
            else {
                System.out.print("Vivants : ");
                p.getVivants().forEach(v -> System.out.print(v.getNom() + " "));
                System.out.println();
            }
        }
    }

    private static void deplacerJoueur() {
        System.out.print("Nom du joueur : ");
        String nom = clavier.nextLine().trim();

        JoueurHumain joueur = null; Piece pieceCourante = null;
        for (Piece p : getPieces())
            for (Vivant v : p.getVivants())
                if (v instanceof JoueurHumain && v.getNom().equals(nom)) { joueur = (JoueurHumain) v; pieceCourante = p; }

        if (joueur == null) { System.out.println("Introuvable."); return; }

        System.out.println("Portes depuis " + pieceCourante.getNom() + " :");
        for (Porte po : pieceCourante.getPortes()) {
            System.out.println(" - " + po.getNom() + " -> " + po.getPieceAutreCote(pieceCourante).getNom());
        }
        System.out.print("Nom porte : ");
        String nomPorte = clavier.nextLine().trim();

        Porte choisie = pieceCourante.getPortes().stream()
                .filter(po -> po.getNom().equals(nomPorte))
                .findFirst().orElse(null);
        if (choisie == null) { System.out.println("Porte inconnue."); return; }

        Piece dest = choisie.getPieceAutreCote(pieceCourante);
        try {
            pieceCourante.sortir(joueur);
            dest.entrer(joueur);
            System.out.println(" " + joueur.getNom() + " → " + dest.getNom());
        } catch (ITIAventureException e) { System.out.println(" " + e.getMessage()); }
    }

    //private static void verifierConditions() {
      //  if (conditions.isEmpty()) { System.out.println("Aucune condition."); return; }
        //boolean fin = false;
    //     for (ConditionDeFin c : conditions) {
    //         if (c.getEtatSiConditionVerifiee()== EtatDuJeu.SUCCES) {
    //             fin = true;
    //             System.out.println("\uD83C\uDF89 Condition " + (c.getEtatSiConditionVerifiee() ? "SUCCES" : "ECHEC") + " atteinte.");
    //         }
    //     }
    //     if (!fin) System.out.println("Rien n’est atteint.");
    // }
}



