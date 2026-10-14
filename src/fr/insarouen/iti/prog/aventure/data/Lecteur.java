package  fr.insarouen.iti.prog.aventure.data; 

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.ITIAventureException;


import java.util.Collection;

public interface Lecteur {
    
    Monde getMonde() throws Throwable;
    Collection<ConditionDeFin> getConditionsDeFin();
}
