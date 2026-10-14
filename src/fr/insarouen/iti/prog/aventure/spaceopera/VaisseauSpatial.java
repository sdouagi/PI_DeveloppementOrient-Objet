package fr.insarouen.iti.prog.aventure.spaceopera;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;

public class VaisseauSpatial extends Piece {
  public VaisseauSpatial(String nom, Monde monde)  throws NomDEntiteDejaUtiliseDansLeMondeException{
    super(nom, monde);
  }
}
