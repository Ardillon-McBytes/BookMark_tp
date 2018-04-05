/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sqlclass.SimpleDataSource;

/**
 *
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_Tag {

    static Tag tag = new Tag();

    /**
     *
     * @return
     */
    static public Tag getTag() {

        return tag;
    }

    void setTag(Tag tag) {

        this.tag = tag;
    }

    /**
     *
     * @param name
     * @param description
     */
    static public void setTag(String name, String description) {

        tag.setNom(name);
        tag.setDescription(description);
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    static public Tag getTagFromId(int id) throws SQLException {
        
        tag = new Tag();
        Connection conn = SimpleDataSource.getConnection();
        String query = "SELECT * "
                + "FROM tag "
                + "WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            tag.setId(rs.getInt(1));
            tag.setNom(rs.getString(2));
            tag.setDescription(rs.getString(3));
           
        }
        return tag;
    }

    static public Tag getTagFromName(String name) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        Tag tag = new Tag();
        try {

            String query = "SELECT * "
                    + "FROM tag "
                    + "WHERE nom = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tag.setId(rs.getInt(1));
                tag.setNom(rs.getString(2));
                tag.setDescription(rs.getString(3));
            }

        } finally {
            conn.close();

        }
        return tag;
    }

    /**
     *
     * @throws SQLException
     */
    static public void addTag() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();

        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `tag` (`nom`, `description`) "
                    + "VALUES ('" + tag.getNom() + "','"
                    + tag.getDescription() + "')");

            stat.executeUpdate();

        } finally {
            conn.close();

        }
    }

    void editTag() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();

        try {

            PreparedStatement stat = conn.prepareStatement(
                    " UPDATE `tag` "
                    + "SET 'nom' = '" + tag.getNom() + "','"
                    + " 'description = '" + tag.getDescription() + "'"
                    + "WHERE 'tag'.'id' = " + tag.getId());

            stat.executeUpdate();

        } finally {
            conn.close();

        }
    }
}
