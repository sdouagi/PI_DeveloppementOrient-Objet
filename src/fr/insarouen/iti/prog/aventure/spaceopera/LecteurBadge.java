package fr.insarouen.iti.prog.aventure.spaceopera;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Serrure;
import fr.insarouen.iti.prog.aventure.elements.objets.serrurerie.Cle;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;

public class LecteurBadge extends Serrure{
  public LecteurBadge(Monde monde) throws NomDEntiteDejaUtiliseDansLeMondeException{
    super(monde);
  }

  @Override
  public Cle creerCle() throws NomDEntiteDejaUtiliseDansLeMondeException {
    if (this.nomCle.equals("")) {
        Badge badge = new Badge(this.getMonde());
        this.nomCle = badge.getNom();
        return badge;
    } else {
        return null;
    }
}

}
