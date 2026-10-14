package fr.insarouen.iti.prog.aventure.elements.structure;

import org.junit.Test;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleAvecObjetException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleException;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurie.Serrure;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.is;

public class TestPorte{

    private static Monde monde;
    private static Piece piece;
    private static Piece piece2;
    private static Porte porte;
    private static Porte porte2;
    private static Porte porte3;
    private static Porte porte4;
    public static Serrure serrure;
    public static Serrure serrure2;
    private static Cle cle;
    private static Cle cle2;
    private static PiedDeBiche pdb;

    @BeforeClass
    static public void  init() throws NomDEntiteDejaUtiliseDansLeMondeException{
        monde = new Monde("pink pony");
        piece = new Piece("rainbow",monde);
        piece2 = new Piece("chambre a gaz",monde);
        porte = new Porte("Porte magique", monde, piece, piece2);
        porte2 = new Porte("Porte arcenciel", monde, piece, piece2);
        serrure = new Serrure("gold",monde);
        serrure2 = new Serrure("ser2",monde);
        porte3 = new Porte("Porte arcenciel", monde,serrure, piece, piece2);
        porte3 = new Porte("Porte arcenciel", monde,serrure, piece, piece2);
        porte4 = new Porte("Porte arc", monde,serrure2, piece, piece2);
        pdb = new PiedDeBiche("pied",monde);
        cle = serrure.creerCle();
        cle2 = serrure2.creerCle();
    }
    
    @Test 
    public void testGetEtat(){
        assertThat(porte.getEtat(), equalTo(Etat.FERME));
    }    

    @Test 
    public void testGetPiece(){
        assertThat(porte.getPieceAutreCote(piece), equalTo(piece2));
    }

    @Test
    public void testGetSerrure(){
        assertThat(porte3.getSerrure(), equalTo(serrure));
    }
        
    @Test 
    public void testActiver() throws ActivationImpossibleException{
        porte2.activer();
        assertThat(porte2.getEtat(), equalTo(Etat.OUVERT));

    }

    @Test
    public void testActiverAvec() throws ActivationImpossibleAvecObjetException{
        porte3.activerAvec(cle);
        assertThat(porte3.getEtat(), equalTo(Etat.OUVERT));
    }

    @Test
    public void testActiverAvecPDB() throws ActivationImpossibleAvecObjetException{
        porte4.activerAvec(pdb);
        assertThat(porte4.getEtat(), equalTo(Etat.OUVERT));
    }
}