package fr.insarouen.iti.prog.aventure.elements.structure;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.is;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;

public class TestPiece{

    private static Monde monde;
    private static Piece piece;
    private static Piece piece2;
    private static Piece piece3;
    private static PiedDeBiche pied;
    private static PiedDeBiche pied2;
    private static PiedDeBiche pied3;
    private static PiedDeBiche pied4;
    private static VivantTest vivant;
    private static VivantTest vivant2;
    private static VivantTest vivant3;
    private static Porte porte;
    private static Porte porte2;

    @BeforeClass
    static public void  init() throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException {
        monde = new Monde("pink pony");
        piece = new Piece("rainbow",monde);
        piece2 = new Piece("chambre a gaz",monde);
        piece3 = new Piece("salle de vol d'oiseau",monde);
        pied = new PiedDeBiche("pdb",monde );
        pied2 = new PiedDeBiche("pdb2",monde );
        pied3 = new PiedDeBiche("pdb3",monde );
        pied4 = new PiedDeBiche("pdb4",monde );
        vivant = new VivantTest("emmanuel", monde, 20,100,piece);
        vivant2 = new VivantTest("gauvain", monde, 20,100,piece);
        vivant3 = new VivantTest("dylan", monde, 20,100,piece3);
        porte = new Porte("Porte magique", monde, piece, piece2);
        porte2 = new Porte("Porte arcenciel", monde, piece, piece2);
        piece.deposer(pied);
        piece.deposer(pied2);
    }


    @Test
    public void testContientObjet(){
        assertThat(piece.contientObjet(pied2), is(true));
    }

    @Test
    public void testGetObjet(){
        assertThat(piece.getObjet("pdb"), equalTo(pied));
    }

    @Test
    public void testGetObjets(){
        assertThat(piece.getObjets(), containsInAnyOrder(pied, pied2));
    }

    @Test
    public void testGetVivant(){
        assertThat(piece3.getVivant("dylan"), equalTo(vivant3));
    }

    @Test
    public void testGetVivants(){
        assertThat(piece3.getVivants(), containsInAnyOrder(vivant3));
    }

    @Test
    public void testGetPorte(){
        assertThat(piece.getPorte("Porte magique"), equalTo(porte));
    }

    @Test
    public void testALaPorte(){
        assertThat(piece.aLaPorte("Porte magique"), is(true));
    }

    @Test
    public void testAddPorte(){
        piece.addPorte(porte2);
        assertThat(piece.aLaPorte(porte2), is(true));
    }

    @Test
    public void testdeposer() throws ObjetNonDeplacableException, ObjetAbsentDeLaPieceException{
        piece.deposer(pied3);
        assertThat(piece.contientObjet(pied3), is(true));
        piece.retirer(pied3);
    }

    @Test
    public void testContientVivant(){
        assertThat(piece.contientVivant(vivant), is(true));
    }

    @Test
    public void testRetirer() throws ObjetNonDeplacableException, ObjetAbsentDeLaPieceException{
        piece.retirer(pied2);
        assertThat(piece.contientObjet(pied2), is(false));
        piece.deposer(pied2);
    }

    @Test
    public void testSortir() throws VivantAbsentDeLaPieceException {
        piece.sortir(vivant);
        assertThat(piece.contientVivant(vivant), is(false));
        piece.entrer(vivant);
    }

    @Test
    public void testEntrer() throws VivantAbsentDeLaPieceException {
        piece2.entrer(vivant2);
        assertThat(piece2.contientVivant(vivant2), is(true));
    }

}

class VivantTest extends Vivant {
    public VivantTest(String nom, Monde monde, int pointVie, int pointForce, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException, VivantAbsentDeLaPieceException {
        super(nom, monde, pointVie, pointForce, piece);
    }
}