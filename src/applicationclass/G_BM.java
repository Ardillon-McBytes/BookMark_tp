/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 *
 * @author olivi
 */
public class G_BM {

  /**
   *
   */
  static public Bookmark bm = new Bookmark();

  /**
   *
   */
  public G_BM() {
    }

  /**
   *
   * @return
   */
  static public Bookmark getBookMark() {

        return bm;
    }

  /**
   *
   * @param bm
   */
  static public void setBookMark(Bookmark bm) {

        G_BM.bm = bm;
    }

  /**
   *
   * @param id
   * @throws SQLException
   */
  public void getBm(int id) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
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
   *
   * @param name
   * @return
   * @throws SQLException
   */
  static public Bookmark getBm(String name) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        
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
   *
   * @throws SQLException
   */
  static public void addBm() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();

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
   *
   * @throws SQLException
   */
  public void editBm() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();

        try {

            PreparedStatement stat = conn.prepareStatement(
                    " UPDATE `bookmark` "
                    + "SET 'nom_site' = '" + bm.getNom() + "','"
                    + " 'Description = '" + bm.getDescription() + "','"
                    + " 'Url = '" + bm.getUrl() + "'"
                    + "WHERE 'bookmark'.'id' = " + bm.getId());

            stat.executeUpdate();

        } finally {
            conn.close();

        }
    }
    
  /**
   *
   * @throws IOException
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  static public void deleteBm()
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " DELETE FROM  `bookmark` "
                    + "WHERE `id` = " + bm.getId());

            stat.executeUpdate();
        } finally {
            conn.close();
        }
    }
}
