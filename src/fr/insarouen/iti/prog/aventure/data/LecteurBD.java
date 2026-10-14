package fr.insarouen.iti.prog.aventure.data;

import fr.insarouen.iti.prog.aventure.ITIAventureException;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;

import java.util.Collection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class LecteurBD implements Lecteur{

  private Connection connection;

  public LecteurBD(Connection connection) throws SQLException {
    this.connection = connection;
  }


  private ResultSet obtenirTable(String nom, Monde monde) throws SQLException{
    return this.connection.prepareStatement(String.format("SELECT * FROM %s WHERE NOM_MONDE = '%s'", nom, monde.getNom())).executeQuery();
  }

  @Override
  public Monde getMonde() throws SQLException, ITIAventureException {
    ResultSet laTable = this.connection.prepareStatement(String.format("SELECT * FROM monde")).executeQuery();

    laTable.next();
    Monde monde = new Monde(laTable.getString("nom"));

    //Les PDB
    laTable = obtenirTable("PIEDDEBICHE", monde);

    while (laTable.next()){
      PiedDeBiche pdb = new PiedDeBiche(laTable.getString("NOM"), monde);
    }

    //Les Pieces
    laTable = obtenirTable("PIECE", monde);

    while (laTable.next()){
          Piece piece = new Piece(laTable.getString("NOM"), monde);
          ResultSet tableCONTIENT_PIECE_PDB = this.connection.prepareStatement(String.format("SELECT * FROM CONTIENT_PIECE_PDB WHERE NOM_PIECE = '%s'", piece.getNom())).executeQuery();
          while (tableCONTIENT_PIECE_PDB.next()){
              piece.deposer((PiedDeBiche)monde.getEntite(laTable.getString("NOM")));
          }
    }


    //Portes
    laTable = obtenirTable("PORTE", monde);
    
    while (laTable.next()) {
      ResultSet tablePOSSEDE_PIECE_PORTE =this.connection.prepareStatement(String.format("SELECT * FROM %s WHERE NOM_PORTE = '%s'", "POSSEDE_PIECE_PORTE", laTable.getString("NOM"))).executeQuery();
      tablePOSSEDE_PIECE_PORTE.next();
      Porte porte = new Porte(laTable.getString("NOM"), monde,(Piece)monde.getEntite(tablePOSSEDE_PIECE_PORTE.getString("NOM_PIECEA")), (Piece)monde.getEntite(tablePOSSEDE_PIECE_PORTE.getString("NOM_PIECEB")));
    }

    //JoueurHumain
    laTable = obtenirTable("JOUEURHUMAIN", monde);

    while (laTable.next()) {
      JoueurHumain jh = new JoueurHumain(laTable.getString("NOM"), 
                                        monde, 
                                        laTable.getInt("PT_DE_VIE"), 
                                        laTable.getInt("PT_DE_FORCE"), 
                                        (Piece)monde.getEntite(laTable.getString("NOM_PIECE")));
               
      ResultSet tablePOSSEDE_PDB_JH = this.connection.prepareStatement(String.format("SELECT * FROM  %s WHERE NOM_JH = '%s'", "POSSEDE_PDB_JH", laTable.getString("NOM"))).executeQuery();

      while (tablePOSSEDE_PDB_JH.next()){
        jh.getPiece().deposer((PiedDeBiche)monde.getEntite(tablePOSSEDE_PDB_JH.getString("NOM_PDB")));
        jh.prendre((PiedDeBiche)monde.getEntite(tablePOSSEDE_PDB_JH.getString("NOM_PDB")));
      }


    }

    return monde;
  }

  public Collection<ConditionDeFin> getConditionsDeFin() {
        return null;
    }


}