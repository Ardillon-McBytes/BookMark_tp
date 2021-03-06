/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import static applicationclass.Gestionnaire.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
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

  /**
   *
   * @param user
   * @throws SQLException
   * @throws IOException
   */
  public G_GB(User user) throws SQLException, IOException {
    constructor(user);
  }

  /**
   *
   * @param racine
   * @throws IOException
   * @throws Exception
   */
  public G_GB(Groupbook racine) throws IOException, Exception {
    if (!G_Validation.gbRacine(racine)) {
      throw new IOException("Dossier non conforme pour être une racine");
    }
    G_GB.racine = racine;
  }

  /**
   *
   * @return @throws Exception
   */
  public static ArrayList<Integer>
          getAllGroupbooks() throws Exception {
    if (racine == null) {
      throw new Exception();
    }
    return racine.getGroupbooks();
  }

  /**
   *
   * @param parent
   * @param nouveau
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static boolean
          add(Groupbook parent, Groupbook nouveau)
          throws SQLException, IOException {
    if (racine.equals(nouveau)) {
      throw new IOException("Il est interdi d'ajouter la racine ");
    }
    return G_TA.addConteneur(parent, nouveau);
  }

  /**
   *
   * @param acceseur
   * @param nouveau
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static boolean
          add(User acceseur, Groupbook nouveau)
          throws SQLException, IOException {
    //return G_TA.addConteneur(parent, nouveau);
    //... en construction
    return false;
  }

  /**
   *
   * @param rs
   * @param parent
   * @return
   * @throws SQLException
   */
  public static Groupbook initGroupbook(ResultSet rs, int parent)
          throws SQLException {
    Groupbook gb = new Groupbook(
            rs.getInt("id"), rs.getString("nom"),
            rs.getString("description"), parent);

    return gb;
  }

  /*
  
  public static Groupbook getRacine(User user) throws Exception {
    Groupbook gb = getUserRacineGroupbook(user);
    if (gb == null) {
      throw new Exception(
              "Aucun groupbook ne possède l'identifiant recherché par l'utilisateur");
    }
    return gb;
  }*/
  /**
   *
   * @param user
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static boolean
          add(User user) throws SQLException, IOException {
    if (Recherche.containsLeft(G_TA.getAcces(), user)) {
      throw new IOException("L'utilisateur possède déjà des Groupbooks");
    }
    return false;//G_TA.addConteneur(parent, nouveau);
  }

  /**
   *
   * @param accedeur
   * @param cible
   * @return
   * @throws SQLException
   */
  public static boolean
          remove(User accedeur, Groupbook cible) throws SQLException {
    return G_TA.addAccess(accedeur, cible);
  }

  /**
   *
   * @param racine
   * @param cible
   * @return
   * @throws SQLException
   */
  public static DBA<Groupbook, Groupbook>
          remove(Groupbook racine, Groupbook cible) throws SQLException {
    return G_TA.removeConteneur(racine, cible);
  }

  /**
   *
   * @param dossier
   * @param bookmark
   * @return
   * @throws SQLException
   */
  public static DBA<Groupbook, Bookmark>
          remove(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return G_TA.removeContenu(dossier, bookmark);
  }

//  public static DBA<Bookmark, Tag> 
//        remove(Bookmark bookmark, Tag tag) throws SQLException {
//    return G_TA.removeEtiquette(bookmark, tag);
//  }
  //public static boolean changerTypeAcces()
  /* @J-A_edits djhfadkfhjladk */
  /**
   *
   * @param user
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  static public void createGb(User user)
          throws IOException, SQLException, ClassNotFoundException {

    conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `group_book` (`nom`, `Description`) "
              + "VALUES ('" + user.getNom() + "','"
              + "Default Group" + "')");

      stat.executeUpdate();
    } finally {
      conn.close();
    }
  }

  /**
   *
   * @param gb_name
   * @param gb_description
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  static public void createGb(String gb_name, String gb_description)
          throws IOException, SQLException, ClassNotFoundException {

    conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `group_book` (`nom`, `Description`) "
              + "VALUES ('" + gb_name + "','"
              + gb_description + "')");

      stat.executeUpdate();
    } finally {
      conn.close();
    }
  }

  static void deleteGb(User user)
          throws IOException, SQLException, ClassNotFoundException {

    conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " DELETE FROM  `group_book` "
              + "WHERE `user`.`id` = " + user.getId());

      stat.executeUpdate();
    } finally {
      conn.close();
    }
  }

  void editGb(Groupbook bm) throws SQLException {
    conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " UPDATE `group_book` "
              + "SET 'Nom' = '" + bm.getNom() + "','"
              + " 'Description = '" + bm.getDescription()
              + "WHERE 'bookmark'.'id' = " + bm.getId());

      stat.executeUpdate();

    } finally {
      conn.close();

    }
  }

  /**
   *
   * @param gb
   * @return
   * @throws SQLException
   */
  static public ArrayList<User>
          getUserFromGB(ArrayList<Groupbook> gb) throws SQLException, IOException, ClassNotFoundException {
    conn = SimpleDataSource.getConnection();
    ArrayList<User> Ar_User = new ArrayList<>();
    try {
      for (int i = 0; i < gb.size(); i++) {

        ArrayList<Integer> users_id = TA_User_GB.getUserId(gb.get(i).getId());
        
        
           for (int j = 0; j < users_id.size(); j++) {
              
               User user = new User();
               user.setId(users_id.get(j));
               user.setNom(G_User.getUserName(users_id.get(j)));
               Ar_User.add(user);
           }
          
        
      }
    } finally {
      conn.close();

    }
    return Ar_User;
  }

  /**
   *
   * @param gb
   * @return
   * @throws SQLException
   * @throws IOException
   */
  static public ArrayList<User>
          getUserFromGBRead(ArrayList<Groupbook> gb) throws SQLException, IOException {
    Connection conn = SimpleDataSource.getConnection();
    ArrayList<User> Ar_User = new ArrayList<User>();
    try {
      for (int i = 0; i < gb.size(); i++) {

        String query = "SELECT * "
                + "FROM bookmark_group "
                + "WHERE id_group = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, gb.get(i).getId());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
          int id_bm = rs.getInt(2);
          String query2 = "SELECT * "
                  + "FROM user_group "
                  + "WHERE id_groupBook = ?";
          PreparedStatement ps2 = conn.prepareStatement(query2);
          ps2.setInt(1, id_bm);

          ResultSet rs2 = ps2.executeQuery();
          int id_user = 0;
          while (rs2.next()) {
            int id_gb = rs2.getInt(4);
            id_user = rs2.getInt(3);
            int id_default_Gb;
            String name = G_User.getUserName(id_user);
            id_default_Gb = getDefaultGbId(name);
            if (gb.get(i).getId() == id_default_Gb) {
              String query3 = "SELECT * "
                      + "FROM user "
                      + "WHERE id = ?";
              PreparedStatement ps3 = conn.prepareStatement(query3);
              ps3.setInt(1, rs2.getInt(3));

              ResultSet rs3 = ps3.executeQuery();

              if (rs3.next()) {
                name = rs3.getString(2);
                User user = new User();
                user.setNom(name);
                Ar_User.add(user);
              }
            }

          }

        }
      }
    } finally {
      conn.close();

    }
    return Ar_User;
  }

  /**
   *
   * @param userId
   * @return
   * @throws SQLException
   */
  static public ArrayList<Groupbook>
          getGBFromUser(int userId) throws SQLException, IOException, ClassNotFoundException {
    Connection conn = SimpleDataSource.getConnection();
    ArrayList<Groupbook> list_gb = new ArrayList<Groupbook>();
     ArrayList<Groupbook> list_gb2 = new ArrayList<Groupbook>();
    Groupbook gb = new Groupbook();
    try {

        list_gb2= TA_User_GB.getUserGroups(userId);
     
      int nb = 0;
        for (int i = 0; i < list_gb2.size(); i++) {
            
       
        gb = new Groupbook();
        gb.setId( list_gb2.get(i).getId());

        String query2 = "SELECT * "
                + "FROM group_book "
                + "WHERE id = ?";
        
        PreparedStatement ps2 = conn.prepareStatement(query2);
        ps2.setInt(1, gb.getId());

        ResultSet rs2 = ps2.executeQuery();

        while (rs2.next()) {
          gb.setId(rs2.getInt(1));
          gb = TA_GB_BM.getBmFromGb(gb.getId());
          gb.setNom(rs2.getString(2));
          list_gb.add(gb);
        }

      }

    } finally {
      conn.close();

    }
    return list_gb;
  }

  /**
   *
   * @param userName
   * @return
   * @throws SQLException
   */
  static public int
          getDefaultGbId(String userName) throws SQLException {
    Connection conn = SimpleDataSource.getConnection();
    Groupbook gb = new Groupbook();
    Bookmark bm;
    try {

      String query = "SELECT id "
              + "FROM group_book "
              + "WHERE nom = ?";
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, userName);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        gb.setId(rs.getInt(1));
      }
    } finally {
      conn.close();

    }
    return gb.getId();
  }

  /**
   *
   * @param gb_name
   * @return
   * @throws SQLException
   */
  static public int
          getGBId(String gb_name) throws SQLException {
    Connection conn = SimpleDataSource.getConnection();
    Groupbook gb = new Groupbook();
    Bookmark bm;
    try {

      String query = "SELECT id "
              + "FROM group_book "
              + "WHERE nom = ?";
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setString(1, gb_name);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        gb.setId(rs.getInt(1));
      }
    } finally {
      conn.close();

    }
    return gb.getId();
  }

  /**
   *
   * @param id
   * @return
   * @throws SQLException
   */
  static public String
          getGBName(int id) throws SQLException {
    Connection conn = SimpleDataSource.getConnection();
    Groupbook gb = new Groupbook();
    Bookmark bm;
    try {

      String query = "SELECT nom "
              + "FROM group_book "
              + "WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        gb.setNom(rs.getString(1));
      }
    } finally {
      conn.close();

    }
    return gb.getNom();
  }
}
