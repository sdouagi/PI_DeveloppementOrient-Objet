package fr.insarouen.iti.prog.aventure.elements.vivants;

import fr.insarouen.iti.prog.aventure.*;
import fr.insarouen.iti.prog.aventure.elements.structure.*;
import fr.insarouen.iti.prog.aventure.elements.objets.*;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.*;
import fr.insarouen.iti.prog.aventure.elements.Etat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;

public class TestJH {

    private Monde monde;
    private Piece hall;
    private Piece salle;
    private Serrure serrure;
    private Porte porte;
    private Cle cle;
    private JoueurHumain joueur;

    @Before
    public void init() throws Exception {
        monde = new Monde("donjon");
        hall = new Piece("Hall", monde);
        salle = new Piece("Salle", monde);
        serrure = new Serrure("serrureTest", monde);
        porte = new Porte("PorteTest", monde, serrure, hall, salle);
        cle = serrure.creerCle();
        hall.deposer(cle);
        joueur = new JoueurHumain("Selma", monde, 10, 5, hall);
    }

    @Test
    public void testPrendreObjet() throws Exception {
        joueur.setOrdre("Prendre " + cle.getNom());
        joueur.executer();
        assertThat(joueur.possede(cle), is(true));
    }

    @Test
    public void testOuvrirPorteAvecCle() throws Exception {
        System.out.println("🔑 Clé réelle : " + cle.getNom());

        joueur.setOrdre("Prendre " + cle.getNom());
        joueur.executer();
        System.out.println("📦 Inventaire après prise : " + joueur.getObjets());

        joueur.setOrdre("OuvrirPorte PorteTest avec " + cle.getNom());
        joueur.executer();

        assertThat(serrure.getEtat(), is(Etat.DEVERROUILLE));
    }

    @Test
    public void testFranchirPorte() throws Exception {
        joueur.setOrdre("Prendre " + cle.getNom());
        joueur.executer();

        joueur.setOrdre("OuvrirPorte PorteTest avec " + cle.getNom());
        joueur.executer();

        joueur.setOrdre("Franchir PorteTest");
        joueur.executer();

        assertThat(joueur.getPiece().getNom(), equalTo("Salle"));
    }

    @Test
    public void testCommandeInvalide() {
        try {
            joueur.setOrdre("Danser Salsa");
            joueur.executer();
            fail("Exception attendue non levée !");
        } catch (CommandeImpossiblePourLeVivantException e) {
            assertThat(e.getMessage(), containsString("Commande impossible"));
        } catch (Exception e) {
            fail("Mauvais type d'exception levé : " + e);
        }
    }
}
