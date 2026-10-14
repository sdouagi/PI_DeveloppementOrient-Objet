package fr.insarouen.iti.prog.aventure.elements.objets.serrurerie;

import fr.insarouen.iti.prog.aventure.elements.objets.serrurie.Cle;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurie.Serrure;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.Etat;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleAvecObjetException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.is;


public class TestSerrure{

    private static Monde monde;
    private static Serrure serrure ;
    private static Serrure serrure2 ;
    private static Cle cle;
    private static Cle cle2;


    @BeforeClass
    static public void  init() throws NomDEntiteDejaUtiliseDansLeMondeException{
       monde = new Monde("pink pony");
       serrure = new Serrure("gold",monde);
       serrure2 = new Serrure("ser",monde);
       cle = serrure.creerCle();
       cle2 = serrure2.creerCle();
    }
    @Test
    public void testEstDeplacable(){
        assertThat(serrure.estDeplacable(), is(false));
    }
    @Test
    public void testGetEtat(){
        assertThat(serrure.getEtat(),equalTo(Etat.VERROUILLE));
    }

    @Test 
    public void testCreeClee(){
        assertThat(monde.getEntite(cle.getNom()), equalTo(cle));
    }

    @Test 
    public void testActivable(){
        assertThat(serrure2.activableAvec(cle2), is(true));
    }

    @Test 
    public void testActiveravec() throws ActivationImpossibleAvecObjetException {
        serrure2.activerAvec(cle2);
        assertThat(serrure2.getEtat(), equalTo(Etat.DEVERROUILLE));
        serrure2.activerAvec(cle2);
        assertThat(serrure2.getEtat(), equalTo(Etat.VERROUILLE));
    }



}