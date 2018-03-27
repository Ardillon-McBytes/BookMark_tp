/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import static applicationclass.G_BM.bm;
import static applicationclass.TA_GB_BM.id_Bm;
import static applicationclass.TA_GB_BM.id_group;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
