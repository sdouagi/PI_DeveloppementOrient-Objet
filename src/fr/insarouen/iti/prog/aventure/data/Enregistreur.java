package  fr.insarouen.iti.prog.aventure.data;

import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;

import java.util.Collection;

public interface Enregistreur{
    void enregistrer(Monde monde, Collection<ConditionDeFin> conditionsDeFin)throws Throwable;

}