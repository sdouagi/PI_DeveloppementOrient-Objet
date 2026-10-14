package fr.insarouen.iti.prog.aventure.data;

import java.io.FileReader;
import java.util.Scanner;
import java.io.Reader;
import java.io.IOException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.data.Lecteur; 
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFinVivantDansPiece;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.EtatDuJeu;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Collection;
import java.util.ArrayList;

public class TestLecteurDescription{

    private static Reader reader;
    private static Lecteur lecteur;
    private static Monde monde2 = new Monde("LeMondeImpitoyabledITI");
    private static ConditionDeFin cdfv;
    private static ConditionDeFin cdfv2;
    private static Piece piece;
    private static Piece piece2;
    private static JoueurHumain jh;
    private static Objet[] obj = null;

    @BeforeClass
    static public void init() throws ITIAventureException, IOException,NomDEntiteDejaUtiliseDansLeMondeException{
        reader = new FileReader("exemplesimulation1.aa");
        lecteur = new LecteurDescription(reader);
        piece = new Piece("BureauDesNicolas",lecteur.getMonde());
        piece2 = new Piece("BureauDuDirecteur",lecteur.getMonde());
        jh = new JoueurHumain("Etudiant1", lecteur.getMonde(), 10, 10,piece,obj);
        cdfv = new ConditionDeFinVivantDansPiece(EtatDuJeu.SUCCES, jh, piece);
        cdfv2 = new ConditionDeFinVivantDansPiece(EtatDuJeu.SUCCES, jh, piece2);
    }

    @Test
    public void testmonde(){
        Monde monde = lecteur.getMonde();
        assertThat(monde.getNom(),equalTo(monde2.getNom()));
    }

    @Test
    public void testCDFV(){
        Collection<ConditionDeFin> cdfv3 = lecteur.getConditionsDeFin();
        System.out.println(cdfv);
        assertThat(cdfv,equalTo(new ArrayList<ConditionDeFin>()));
    }
    
}
