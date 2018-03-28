/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sqlclass.SimpleDataSource;

/**
 * Classe en charge de la gestion des bookmarks (BM, marquepages)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_BM {

  /**
   * Bookmark utilisé pour la gestion
   */
  static public Bookmark bm = new Bookmark();
  private static Connection conn;

  /**
   * Constructeur
   */
  public G_BM() {
  }

  /**
   * Obtient le bookmark du gestionnaire
   *
   * @return Le bookmark
   */
  static public Bookmark getBookMark() {

    return bm;
  }

  /**
   * Modifie le bookmark du gestionnaire
   *
   * @param bm Nouveau bookmark du gestionnaire
   */
  static public void setBookMark(Bookmark bm) {

    G_BM.bm = bm;
  }

  /**
   * Obtient le bookmark associé à l'identifiant
   *
   * @param id Identifiant du bookmark recherché
   * @throws SQLException Erreur causé par la BD
   */
  public static void getBm(int id) throws SQLException {
    conn = SimpleDataSource.getConnection();
    try {

      String query3 = "SELECT * "
              + "FROM bookmark "
              + "WHERE id = ?";
      PreparedStatement ps3 = conn.prepareStatement(query3);
      ps3.setInt(1, id);

      ResultSet rs = ps3.executeQuery();

      if (rs.next()) {

        bm.setId(rs.getInt(1));
        bm.setNom(rs.getString(2));
        bm.setDescription(rs.getString(3));
        bm.setUrl(rs.getString(4));
      }

    } finally {
      conn.close();
    }
  }

  /**
   * Obtient le bookmark ayant le nom sélectionné
   *
   * @param name Nom du bookmark
   * @return Bookmark recherché
   * @throws SQLException Erreur causé par la BD
   */
  static public Bookmark getBm(String name) throws SQLException {
    conn = SimpleDataSource.getConnection();

    try {

      String query3 = "SELECT * "
              + "FROM bookmark "
              + "WHERE nom_site = ?";
      PreparedStatement ps3 = conn.prepareStatement(query3);
      ps3.setString(1, name);

      ResultSet rs = ps3.executeQuery();

      if (rs.next()) {

        bm.setId(rs.getInt(1));
        bm.setNom(rs.getString(2));
        bm.setDescription(rs.getString(3));
        bm.setUrl(rs.getString(4));
      }

    } finally {
      conn.close();
    }
    return bm;
  }

  /**
   * Ajoute un bookmark à la BD
   *
   * @throws SQLException Erreur causé par la BD
   */
  static public void addBm() throws SQLException {
    conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `bookmark` (`nom_site`, `Description`,`Url`) "
              + "VALUES ('" + bm.getNom() + "','"
              + bm.getDescription() + "','"
              + bm.getUrl() + "')");

      stat.executeUpdate();

    } finally {
      conn.close();

    }
  }

  /**
   * Modifie les données du bookmark du gestionnaire
   *
   * @param id_old
   * @param newBm
   * @throws SQLException Erreur causé par la BD
   */
  static public void editBm(int id_old, Bookmark newBm) throws SQLException {
    conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " UPDATE `bookmark` "
              + "SET `nom_site` = " + newBm.getNom() + "','"
              + " `Description = " + newBm.getDescription() + "','"
              + " `Url = `" + newBm.getUrl() + "'"
              + "WHERE 'id' = " + id_old);

      stat.executeUpdate();

    } finally {
      conn.close();

    }
  }

  /**
   * Supprime le bookmark du gestionnaire de la BD
   *
   * @throws IOException Mauvaise saisie pour être conforme à la reqête
   * @throws SQLException Erreur causé par la BD
   * @throws ClassNotFoundException La classe de l'objet n'est pas trouvé
   */
  static public void deleteBm()
          throws IOException, SQLException, ClassNotFoundException {

    conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " DELETE FROM  `bookmark` "
              + "WHERE `id` = " + bm.getId());

      stat.executeUpdate();

      bm.setNom("");
      bm.setDescription("");
      bm.setUrl("");
      bm.setValue("");
    } finally {
      conn.close();
    }
  }
}
