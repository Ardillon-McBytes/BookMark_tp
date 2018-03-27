/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sqlclass.SimpleDataSource;

/**
 *
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_Partage {

  void addUserGroup(int type, User user, Groupbook gb)
          throws SQLException {
    Connection conn = SimpleDataSource.getConnection();

    try {

      PreparedStatement stat = conn.prepareStatement(
              " INSERT INTO `user_group` (`id_type`, `id_user`, `id_groupBook`) "
              + "VALUES ('"
              + type + "','"
              + user.getId() + "','"
              + gb.getId() + "')");

      stat.executeUpdate();
      System.exit(0);

    } finally {
      conn.close();

    }
  }

  void deleteUserFromGroup(User user, Groupbook gb)
          throws IOException, SQLException, ClassNotFoundException {

    Connection conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " DELETE FROM  `user_group` "
              + "WHERE `id_user` = " + user.getId()
              + " AND `id_groupBook` = " + gb.getId());

      stat.executeUpdate();
    } finally {
      conn.close();
    }
  }

  void deleteGroup(Groupbook gb)
          throws IOException, SQLException, ClassNotFoundException {

    Connection conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              " DELETE FROM  `user_group` "
              + "WHERE `id_groupBook` = " + gb.getId());

      stat.executeUpdate();
    } finally {
      conn.close();
    }
  }
}
