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
 * @author olivi
 */
public class GestionnaireGB {

    static void createGb(User user)
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
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

    static void deleteGb(User user)
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
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
        Connection conn = SimpleDataSource.getConnection();

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
}
