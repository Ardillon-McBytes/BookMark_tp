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
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import sqlclass.SimpleDataSource;

/**
 * Classe d'association entre les utilisateur et les dossiers de marquepage
 * (Partages et propriétaires)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_User_GB extends TABase<User, Groupbook, DBA<User, Groupbook>> {

  /**
   *
   */
  public TA_User_GB() {
    TABase.constructor("user_group",
            1, "id",
            4, "id_user",
            2, "id_groupBook");
  }

  /**
   *
   * @param id_user
   * @param id_groupBook
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  static public void deleteUserGroup(int id_user, int id_groupBook) throws IOException, SQLException, ClassNotFoundException {

    Connection conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " DELETE FROM  `user_group` "
              + "WHERE `id_user` = " + id_user + " AND `id_groupBook` = " + id_groupBook);

      stat.executeUpdate();
    } finally {
      conn.close();
    }

  }

  /**
   *
   * @param id_user
   * @param id_groupBook
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  static public void addUserGroup(int id_user, int id_groupBook) throws IOException, SQLException, ClassNotFoundException {

    Connection conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `user_group` (`id_user`, `id_groupBook`) "
              + "VALUES ('" + id_user + "','"
              + id_groupBook + "')");

      stat.executeUpdate();

    } finally {
      conn.close();

    }

  }

  static public ArrayList<Groupbook> getUserGroups(int id_user) throws IOException, SQLException, ClassNotFoundException {

    ArrayList<Groupbook> gb = new ArrayList<>();
    Connection conn = SimpleDataSource.getConnection();
    try {

      String query = "SELECT * "
              + "FROM user_group "
              + "WHERE id_user = ?";
      PreparedStatement ps = conn.prepareStatement(query);
      ps.setInt(1, id_user);

      ResultSet rs = ps.executeQuery();

      G_BM G_BM = new G_BM();
      int n = 0;
      while (rs.next()) {
        G_BM = new G_BM();
        Groupbook gb2 = new Groupbook();

        //@old-node_conflict Il semblerait que la méthode retourne un bookmark, mais on veut un groupbook
        //gb2 = TA_GB_BM.getBmFromGb(rs.getInt(4));
        gb.add(gb2);

        n++;
      }

    } finally {
      conn.close();

    }

    return gb;

  }
}
