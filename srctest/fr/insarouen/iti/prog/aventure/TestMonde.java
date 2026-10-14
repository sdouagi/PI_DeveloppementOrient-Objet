package fr.insarouen.iti.prog.aventure;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.is;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;

public class TestMonde{
    
    private static Monde monde;
    private static Monde monde1;
    private static EntiteTest entite;
    private static EntiteTest entite2;
    private static EntiteTest entite3;
    static String nom;

    @BeforeClass
    static public void  init() throws NomDEntiteDejaUtiliseDansLeMondeException{
        monde = new Monde("pink pony");
        monde1 = new Monde("pink pony1");
        entite = new EntiteTest("licorne", monde);
        entite2 = new EntiteTest("poney", monde);
        nom = monde1.generateNom("entite");
        entite3 = new EntiteTest(nom, monde1 );
    }

    @Test
    public void testGetNom(){
        assertThat(monde.getNom(), equalTo("pink pony"));
    }

    @Test
    public void testGetEntite() throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException{
        assertThat(monde.getEntite("licorne"), equalTo(entite));
    }

    @Test
    public void testAjouterEntite() throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException{
        entite2 = new EntiteTest("T-rex", monde);
        assertThat(monde.getEntite("T-rex"), equalTo(entite2));
    }
    
    @Test
    public void testGetEntites() throws NomDEntiteDejaUtiliseDansLeMondeException, EntiteDejaDansUnAutreMondeException{
        assertThat(monde.getEntites(), containsInAnyOrder(entite, entite2));
    }

    @Test
    public void testgenerate(){
        assertThat(monde1.getEntite(entite3.getNom()), equalTo(entite3));
    }


}

class EntiteTest extends Entite {
    public EntiteTest(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        super(nom, monde);
    }
}