package fr.insarouen.iti.prog.aventure.elements;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.insarouen.iti.prog.aventure.elements.TestEntite;
import fr.insarouen.iti.prog.aventure.elements.vivants.TestVivant;
import fr.insarouen.iti.prog.aventure.elements.structure.TestPiece;
import fr.insarouen.iti.prog.aventure.elements.structure.TestPorte;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestEntite.class,
    TestPiece.class,
    TestVivant.class,
    TestPorte.class
    })
public class AllTestsElements {}