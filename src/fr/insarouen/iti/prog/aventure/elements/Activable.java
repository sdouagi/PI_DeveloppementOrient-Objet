package fr.insarouen.iti.prog.aventure.elements;

import fr.insarouen.iti.prog.aventure.elements.objets.Objet;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleAvecObjetException;
import fr.insarouen.iti.prog.aventure.elements.ActivationImpossibleException;
/**
 * Interface reprÃ©sentant un Ã©lÃ©ment activable dans le jeu.
*/
public interface Activable {

    /**
     * Cette mÃ©thode est utilisÃ©e pour activer l'Ã©lÃ©ment sans nÃ©cessiter d'objet.
     
     */
    public void activer() throws ActivationImpossibleException;

    /**
     * Active l'Ã©lÃ©ment avec un objet spÃ©cifique.
     
     * @param objet L'objet avec lequel l'Ã©lÃ©ment doit Ãªtre activÃ©.
     * @throws ActivationImpossibleAvecObjetException Si l'activation avec cet objet est impossible.
     */
    public void activerAvec(Objet objet) throws ActivationImpossibleAvecObjetException;

    /**
     * VÃ©rifie si l'Ã©lÃ©ment peut Ãªtre activÃ© avec un objet donnÃ©.
     
     * @param objet L'objet avec lequel vÃ©rifier l'activation.
     * @return {@code true} si l'Ã©lÃ©ment peut Ãªtre activÃ© avec cet objet, {@code false} sinon.
     */
    public boolean activableAvec(Objet objet);
}

