package fr.insarouen.iti.prog.aventure.data;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.lang.ClassNotFoundException;
import java.util.stream.Stream;
import fr.insarouen.iti.prog.aventure.Monde;
import fr.insarouen.iti.prog.aventure.conditions.ConditionDeFin;
import fr.insarouen.iti.prog.aventure.elements.structure.Piece;
import fr.insarouen.iti.prog.aventure.elements.Entite;
import fr.insarouen.iti.prog.aventure.elements.structure.Porte;
import fr.insarouen.iti.prog.aventure.elements.objets.PiedDeBiche;
import fr.insarouen.iti.prog.aventure.elements.vivants.JoueurHumain;
import fr.insarouen.iti.prog.aventure.elements.objets.Objet;

import java.util.stream.Collectors;

import java.util.Collection;
import java.util.List;
import java.util.Iterator;


public class EnregistreurBD implements Enregistreur{

    private Connection connection;

    private PreparedStatement pstMONDE;
    private PreparedStatement pstPIECE;
    private PreparedStatement pstJOUEURHUMAIN;
    private PreparedStatement pstPIEDDEBICHE;
    private PreparedStatement pstPORTE;
    private PreparedStatement pstPOSSEDE_PIECE_PORTE;
    private PreparedStatement pstCONTIENT_PIECE_PDB;
    private PreparedStatement pstPOSSEDE_PDB_JH;

    public EnregistreurBD(Connection connection) throws SQLException, ClassNotFoundException {
        this.connection = connection;
        this.pstMONDE = connection.prepareStatement("INSERT INTO MONDE(nom) Values(?)");
        this.pstPIECE = connection.prepareStatement("INSERT INTO PIECE(nom, nom_monde) Values(?, ?)");
        this.pstJOUEURHUMAIN = connection.prepareStatement("INSERT INTO JOUEURHUMAIN(nom, pt_de_vie, pt_de_force, nom_monde, nom_piece) Values(?, ?, ?, ?, ?)");
        this.pstPIEDDEBICHE = connection.prepareStatement("INSERT INTO PIEDDEBICHE(nom, est_deplacable, nom_monde) Values(?, ?, ?)");
        this.pstPORTE = connection.prepareStatement("INSERT INTO PORTE(nom, nom_monde, etat) Values(?, ?, ?)");
        this.pstPOSSEDE_PIECE_PORTE = connection.prepareStatement("INSERT INTO POSSEDE_PIECE_PORTE(nom_piecea, nom_pieceb, nom_porte) Values(?, ?, ?)");
        this.pstCONTIENT_PIECE_PDB = connection.prepareStatement("INSERT INTO CONTIENT_PIECE_PDB(nom_piece, nom_pdb) Values(?, ?)");
        this.pstPOSSEDE_PDB_JH = connection.prepareStatement("INSERT INTO POSSEDE_PDB_JH(nom_pdb, nom_jh) Values(?, ?)");
    }

    @Override
    public void enregistrer(Monde monde, Collection<ConditionDeFin> conditionsDeFin) {

        Collection<Entite> entites = monde.getEntites();
        List<Piece> pieces = entites.stream().filter(p -> p instanceof Piece).map(p -> (Piece) p).collect(Collectors.toList());
        List<JoueurHumain> jhs = entites.stream().filter(p -> p instanceof JoueurHumain).map(p -> (JoueurHumain) p).collect(Collectors.toList());
        List<Porte> portes = entites.stream().filter(p -> p instanceof Porte).map(p -> (Porte) p).collect(Collectors.toList());

        try {
            //Monde
            this.creerMonde(monde.getNom());
            //Les Pieces
            this.creerLesPieces(pieces);
            //Les Portes
            this.creerLesPortes(portes);
            //Les JH
            this.creerLesJH(jhs);

            } catch (SQLException e){
                // update snn 
                System.err.println(String.format("---> %s ",e.getMessage()));
        }
    }

    /*

        TABLES des Classes

    */

    //Monde
    private void creerMonde(String nom) throws SQLException{
        pstMONDE.setString(1, nom);
        pstMONDE.executeUpdate();
    }

    //Piece
    private void creerLesPieces(List<Piece> pieces) throws SQLException {

        for (Piece laPiece : pieces) {
            creerPiece(laPiece);

        }
    }

    private void creerPiece(Piece piece) throws SQLException{
        pstPIECE.setString(1, piece.getNom());
        pstPIECE.setString(2, piece.getMonde().getNom());

        for (Objet o : piece.getObjets()) {
            if (o instanceof PiedDeBiche) {
                PiedDeBiche pdb = (PiedDeBiche) o;
                this.creerPdb(pdb);
                this.creerCONTIENT_PIECE_PDB(piece, pdb);
            }
        }
        pstPIECE.executeUpdate();
    }

    //Joueur Humain
    private void creerJH(JoueurHumain jh) throws SQLException {
        pstJOUEURHUMAIN.setString(1, jh.getNom());
        pstJOUEURHUMAIN.setInt(2, jh.getPointVie());
        pstJOUEURHUMAIN.setInt(3, jh.getPointForce());
        pstJOUEURHUMAIN.setString(4, jh.getMonde().getNom());
        pstJOUEURHUMAIN.setString(5, jh.getPiece().getNom());
        pstJOUEURHUMAIN.executeUpdate();

        for (Objet o : jh.getObjets()) {
            if (o.getClass().equals(PiedDeBiche.class)) {
                PiedDeBiche pdb = (PiedDeBiche) o;
                this.creerPdb(pdb);
                this.creerPOSSEDE_PDB_JH(pdb, jh);
            }
        }
    }


    private void creerLesJH(List<JoueurHumain> jhs) throws SQLException {

        for (JoueurHumain jh : jhs) {
            creerJH(jh);
        }
    }

    //Pied de Biche
    private void creerPdb(PiedDeBiche pdb) throws SQLException {
        this.pstPIEDDEBICHE.setString(1, pdb.getNom());
        this.pstPIEDDEBICHE.setBoolean(2, pdb.estDeplacable());
        this.pstPIEDDEBICHE.setString(3, pdb.getMonde().getNom());
        this.pstPIEDDEBICHE.executeUpdate();
    }

    private void creerLesPDB(List<PiedDeBiche> pdbs) throws SQLException {

        for (PiedDeBiche pdb : pdbs) {
            creerPdb(pdb);
        }
    }

    //Porte
    private void creerPorte(Porte porte) throws SQLException {
        this.pstPORTE.setString(1, porte.getNom());
        this.pstPORTE.setString(2, porte.getMonde().getNom());
        this.pstPORTE.setString(3, porte.getEtat().toString());
        this.pstPORTE.executeUpdate();
        creerPOSSEDE_PIECE_PORTE(porte.getPieceA(),porte.getPieceAutreCote(porte.getPieceA()), porte);
    }

    private void creerLesPortes(List<Porte> portes) throws SQLException {
        for (Porte porte : portes) {
            creerPorte(porte);
        }
    }

    /*

        TABLES des Associations

    */

    //POSSEDE_PIECE_PORTE
    private void creerPOSSEDE_PIECE_PORTE(Piece pieceA, Piece pieceB, Porte porte) throws SQLException {
        this.pstPOSSEDE_PIECE_PORTE.setString(1, pieceA.getNom());
        this.pstPOSSEDE_PIECE_PORTE.setString(2, pieceB.getNom());
        this.pstPOSSEDE_PIECE_PORTE.setString(3, porte.getNom());
        this.pstPOSSEDE_PIECE_PORTE.executeUpdate();
    }

   //CONTIENT_PIECE_PDB
    private void creerCONTIENT_PIECE_PDB(Piece piece, PiedDeBiche pdb) throws SQLException {
        this.pstCONTIENT_PIECE_PDB.setString(1, piece.getNom());
        this.pstCONTIENT_PIECE_PDB.setString(2, pdb.getNom());
        this.pstCONTIENT_PIECE_PDB.executeUpdate();
    }

    //POSSEDE_PDB_JH
    private void creerPOSSEDE_PDB_JH(PiedDeBiche pdb, JoueurHumain jh) throws SQLException {
        this.pstPOSSEDE_PDB_JH.setString(1, pdb.getNom());
        this.pstPOSSEDE_PDB_JH.setString(2, jh.getNom());
        this.pstPOSSEDE_PDB_JH.executeUpdate();
    }

    /*
    private void creerPiece(Piece piece) throws SQLException{
        entites = monde.getEntites();
        for (Entite entite : entites.values()) {

            if (entite instanceof Piece) {
                pstMONDE.setString(1, "PIECE");
                //pstMONDE.setString(2, monde.);
            }

            
        }
        
    }

 
    private void creerPorte(Monde monde) throws SQLException{
        pstMONDE.setString(1, "PORTE");
        pstMONDE.setString(2, nom);
    }
    */
}
