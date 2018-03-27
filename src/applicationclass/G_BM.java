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

    static public Bookmark bm = new Bookmark();

    public G_BM() {
    }

    static public Bookmark getBookMark() {

        return bm;
    }

    static public void setBookMark(Bookmark bm) {

        G_BM.bm = bm;
    }

    static public void getBm(int id) throws SQLException {
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

   static  public void editBm(int id_old, Bookmark newBm) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();

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
