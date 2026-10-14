package fr.insarouen.iti.prog.aventure.data;

import java.io.ObjectInputStream;
import java.io.IOException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import java.util.Collection;
import fr.insarouen.iti.prog.aventure.ITIAventureException;

/**
 * Classe LecteurSerialisation
 * 
 * Cette classe permet de lire un objet Monde sérialisé à partir d'un flux d'entrée.
 * * @author Selma DOUAGI et Anass YOMNI
 */
public class LecteurSerialisation extends Object implements Lecteur{

    private ObjectInputStream ois;
    private Collection<ConditionDeFin> cdfv;
    private Monde monde; 

    /**
     * Constructeur
     *
     *  
     * @param ois : flux d'entrée contenant les données sérialisées 
     * 
     */
    public LecteurSerialisation(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        this.ois = ois;
        this.monde = (Monde) this.ois.readObject();
        this.cdfv = (Collection<ConditionDeFin>) this.ois.readObject();
    }

     /**
     * Retourne le monde désérialisé
     * 
     * @return le monde restauré à partir du fichier sérialisé
     */
    @Override
    public Monde getMonde() throws ITIAventureException {
        if (this.monde == null){
            throw new ITIAventureException("monde vide");
        }
        return this.monde;
    }

    @Override
    public Collection<ConditionDeFin> getConditionsDeFin(){
        return this.cdfv;
    }
}