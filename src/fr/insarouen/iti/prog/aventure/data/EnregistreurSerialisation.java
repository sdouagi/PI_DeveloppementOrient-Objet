package fr.insarouen.iti.prog.aventure.data;

import java.io.ObjectOutputStream;
import java.io.IOException;
import fr.insarouen.iti.prog.aventure.Monde;
import java.util.Collection;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;

/**
 * Classe Vivant
 * 
 * @author Selma DOUAGI et Anass YOMNI
 */
public class EnregistreurSerialisation extends Object implements Enregistreur{

    private ObjectOutputStream oos;

    // Constructeur
    /**
     * Crée un enregistreur avec un flux.
     *
     * @param oos le flux dans lequel écrire les objets.
     * 
     */
    public EnregistreurSerialisation(ObjectOutputStream oos) throws IOException{
        this.oos = oos;
    }

    /**
     * Enregistre le monde et les conditions de fin dans le fichier.
     *
     * @param monde le monde à enregistrer.
     * @param conditionsDeFin Collection des conditions de fin.
     * .
     */
    @Override
    public void enregistrer(Monde monde, Collection<ConditionDeFin> conditionsDeFin) throws IOException {
        oos.writeObject(monde);
        oos.writeObject(conditionsDeFin);
        oos.flush();
    }
}