package fr.insarouen.iti.prog.aventure.elements.vivants;


import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.BeforeClass;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import java.beans.Transient;

import fr.insarouen.iti.prog.aventure.elements.vivants.Vivant;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.EntiteDejaDansUnAutreMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.ObjetAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.objets.ObjetNonDeplacableException;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteInexistanteDansLaPieceException;
import fr.insarouen.iti.prog.aventure.elements.structure.PorteFermeException;

public class TestVivant{

    private static Monde monde;
    private static Piece piece;
    private static Piece piece2;
    private static VivantTest vivant;
    private static VivantTest vivant2;
    private static VivantTest vivant3;
    private static VivantTest vivant4;
    private static VivantTest vivant5;
    private static PiedDeBiche pied;
    private static PiedDeBiche pied2;
    private static PiedDeBiche pied3;
    private static PiedDeBiche pied4;
    private static Porte porte;
    private static Porte porte2;

    @BeforeClass
    static public void  init() throws NomDEntiteDejaUtiliseDansLeMondeException,VivantAbsentDeLaPieceException  {
        monde = new Monde("pink pony");
        piece = new Piece("rainbow",monde);
        piece2 = new Piece("salle rose",monde);
        vivant = new VivantTest("emmanuel", monde, 20,100,piece);
        vivant2 = new VivantTest("gauvain", monde, 0,100,piece);
        vivant3 = new VivantTest("dylan", monde, 100,100,piece);
        vivant4 = new VivantTest("selma", monde, 80,100,piece);
        vivant5 = new VivantTest("kawtar", monde, 1000,100,piece);
        pied = new PiedDeBiche("pdb",monde);
        pied2 = new PiedDeBiche("pdb2",monde);
        pied3 = new PiedDeBiche("pdb3",monde);
        pied4 = new PiedDeBiche("pdb4",monde);
        porte = new Porte("Porte magique", monde, piece, piece2);
    }

    @Test
    public void testGetPointVie(){
        assertThat(vivant.getPointVie(), equalTo(20));
    }

    @Test
    public void testGetPointForce(){
        assertThat(vivant.getPointForce(), equalTo(100));
    }

    @Test
    public void testGetPiece(){
        assertThat(vivant.getPiece(), equalTo(piece));
    }
    
    @Test
    public void testGetobjet() throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableException{
        piece.deposer(pied);
        vivant.prendre(pied);
        assertThat(vivant.getObjet(pied.getNom()), equalTo(pied));
    }

    @Test
    public void testGetObjets() throws ObjetAbsentDeLaPieceException, ObjetNonDeplacableException{
        piece.deposer(pied2);
        vivant.prendre(pied2);
        assertThat(vivant.getObjets(), containsInAnyOrder(pied, pied2));
    }

    @Test
    public void testMort(){
        assertThat(vivant2.estMort("gauvain"), is(true));
    }

    @Test
    public void testPossede(){
        assertThat(vivant.possede(pied), is(true));
    }

    @Test
    public void testPrendre() throws ObjetAbsentDeLaPieceException,ObjetNonDeplacableException{
        piece.deposer(pied3);
        vivant3.prendre(pied3);
        assertThat(vivant3.possede(pied3), is(true));
        assertThat(piece.contientObjet(pied3), is(false));
    }

    @Test
    public void testDeposer() throws ObjetAbsentDeLaPieceException,ObjetNonDeplacableException, ObjetNonPossedeParLeVivantException{
        piece.deposer(pied4);
        vivant3.prendre(pied4);
        vivant3.deposer(pied4);
        assertThat(vivant3.possede(pied4), is(false));
        assertThat(piece.contientObjet(pied4), is(true));
    }

    @Test
    public void testFranchir() throws VivantAbsentDeLaPieceException, PorteInexistanteDansLaPieceException, PorteFermeException{
        porte.activer();
        vivant4.franchir(porte);
        assertThat(vivant4.getPiece(),equalTo(piece2));
        assertThat(piece.contientVivant(vivant4), is(false));
        assertThat(piece2.contientVivant(vivant4), is(true));
    }
    
}

class VivantTest extends Vivant {
    public VivantTest(String nom, Monde monde, int pointVie, int pointForce, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException , VivantAbsentDeLaPieceException  {
        super(nom, monde, pointVie, pointForce, piece);
    }
}

