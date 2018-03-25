/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 *
 * @author olivi
 */
public class G_GB {

  private static Groupbook racine;
  private static Connection conn;

  private static int getNextGroupbookId() throws SQLException {
    conn = SimpleDataSource.getConnection();

    try {
      Statement stat = conn.createStatement();
      ResultSet rs = stat.executeQuery("SELECT MAX(" + G_TA.getAcces().getColId()
              + ") FROM " + G_TA.getAcces().getNomTable());

      rs.next();
      return rs.getInt(1) + 1;
    } catch (SQLException e) {
      return -1;
    } finally {
      conn.close();
    }
  }

  private boolean constructor(User user) throws SQLException, IOException {
    if (user == null) {
      throw new IOException();
    }
    // S'assurer que l'utilisateur est sauvegardé dans l'application et la BD avant
    int id = getNextGroupbookId();
    if (id == -1) {
      G_GB.racine = new Groupbook(id, "racine", "Répertoire racine de l'utilisateur", id,
              new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
      return false;
    } else {
      return true;
    }
  }

  public G_GB(User user) throws SQLException, IOException {
    constructor(user);
  }

  public G_GB(Groupbook racine) throws IOException, Exception {
    if (!G_Validation.gbRacine(racine)) {
      throw new IOException("Dossier non conforme pour être une racine");
    }
    G_GB.racine = racine;
  }

  public static ArrayList<Integer>
          getAllGroupbooks() throws Exception {
    if (racine == null) {
      throw new Exception();
    }
    return racine.getGroupbooks();
  }

  public static boolean
          add(Groupbook parent, Groupbook nouveau)
          throws SQLException, IOException {
    if (racine.equals(nouveau)) {
      throw new IOException("Il est interdi d'ajouter la racine ");
    }
    return G_TA.addConteneur(parent, nouveau);
  }

  public static boolean
          add(User acceseur, Groupbook nouveau)
          throws SQLException, IOException {
    //return G_TA.addConteneur(parent, nouveau);
    //... en construction
    return false;
  }

  public static Groupbook getRacine(User user) throws Exception {
    Groupbook gb = Gestionnaire.getUserRacineGroupbook(user);
    if (gb == null) {
      throw new Exception(
              "Aucun groupbook ne possède l'identifiant recherché par l'utilisateur");
    }
    return gb;
  }

  public static boolean
          add(User user) throws SQLException, IOException {
    if (Recherche.containsLeft(G_TA.getAcces(), user)) {
      throw new IOException("L'utilisateur possède déjà des Groupbooks");
    }
    return false;//G_TA.addConteneur(parent, nouveau);
  }

  public static boolean
          remove(User accedeur, Groupbook cible) throws SQLException {
    return G_TA.addAccess(accedeur, cible);
  }

  public static DBTA<Groupbook, Groupbook>
          remove(Groupbook racine, Groupbook cible) throws SQLException {
    return G_TA.removeConteneur(racine, cible);
  }

  public static DBTA<Groupbook, Bookmark>
          remove(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return G_TA.removeContenu(dossier, bookmark);
  }

//  public static DBTA<Bookmark, Tag> 
//        remove(Bookmark bookmark, Tag tag) throws SQLException {
//    return G_TA.removeEtiquette(bookmark, tag);
//  }
  //public static boolean changerTypeAcces()
}
