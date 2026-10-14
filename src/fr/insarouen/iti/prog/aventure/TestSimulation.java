package fr.insarouen.iti.prog.aventure.data;

import fr.insarouen.iti.prog.aventure.data.LecteurDescription;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.ITIAventureFactory;
import fr.insarouen.iti.prog.aventure.ITIAventureFactoryCreation;


import java.io.FileReader;

public class TestSimulation {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("exemplesimulation2.aa");
            LecteurDescription lecteur = new LecteurDescription(fr);
            Monde monde = lecteur.getMonde();

            System.out.println("c est bien");
            System.out.println("Contenu du monde :\n" + monde);

            
            }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
