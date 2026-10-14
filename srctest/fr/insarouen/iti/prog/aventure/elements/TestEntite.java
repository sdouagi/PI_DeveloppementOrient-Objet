package fr.insarouen.iti.prog.aventure.elements;


import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.BeforeClass;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;

public class TestEntite {

    private static Monde monde;
    private static Monde monde2;
    private static EntiteTest entite;
    private static EntiteTest entite2;

    @BeforeClass
    static public void init() throws NomDEntiteDejaUtiliseDansLeMondeException{
        monde = new Monde("pink pony"); 
        monde2 = new Monde("blue pony");

        entite = new EntiteTest("cheval", monde);
        entite2 = new EntiteTest("cheval", monde2);
    } 

    @Test 
    public void testGetNom(){
        assertThat(entite.getNom(),equalTo("cheval"));
    }

    @Test 
    public void testGetmMonde(){
        assertThat(entite.getMonde(),equalTo(monde));
    }

    @Test 
    public void testEquals(){
        assertThat(entite,equalTo(entite));
    }

}

class EntiteTest extends Entite {
    public EntiteTest(String nom, Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException {
        super(nom, monde);
    }
}