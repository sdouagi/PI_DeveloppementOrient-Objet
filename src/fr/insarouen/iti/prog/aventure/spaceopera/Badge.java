package fr.insarouen.iti.prog.aventure.spaceopera;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;

public class Badge extends Cle {
  public Badge(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
    super(monde);
  }
}
