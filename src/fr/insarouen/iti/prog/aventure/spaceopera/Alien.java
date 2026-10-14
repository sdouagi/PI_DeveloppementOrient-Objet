package fr.insarouen.iti.prog.aventure.spaceopera;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.structure.VivantAbsentDeLaPieceException;
import fr.insarouen.iti.prog.aventure.NomDEntiteDejaUtiliseDansLeMondeException;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.vivants.Monstre;

public class Alien extends Monstre {
  public Alien(String nom, Monde monde, int pointsVie, int pointsForce, Piece piece) throws NomDEntiteDejaUtiliseDansLeMondeException , VivantAbsentDeLaPieceException {
    super(nom, monde, pointsVie, pointsForce, piece);
  }
}
