/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import static applicationclass.G_BM.bm;
import static applicationclass.TA_GB_BM.id;
import static applicationclass.TA_GB_BM.id_Bm;
import static applicationclass.TA_GB_BM.id_group;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlclass.SimpleDataSource;

/**
 * Classe d'association entre les marquepages et les dossiers de marquepage
 * (Arborescence)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_GB_BM extends TABase<Groupbook, Bookmark, DBA<Groupbook, Bookmark>> {

    static public int id = 0;
    static public int id_group;
    static public int id_Bm;

    static public int getId() {
        return id;
    }

    static public int getIdGb() {
        return id_group;
    }

    static public int getIdBm() {
        return id_Bm;
    }

    static public void setId(int id) {
        TA_GB_BM.id = id;
    }

    static public void setIdGb(int id) {
        TA_GB_BM.id_group = id;
    }

    static public void setIdBm(int id) {
        TA_GB_BM.id_Bm = id;
    }

    public TA_GB_BM() {
        TABase.constructor("bookmark_group",
                1, "id",
                3, "id_bookmark",
                2, "id_group");
    }

    static public void add_GB_BM() throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();

        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `bookmark_group` (`id_group`, `id_bookmark`) "
                    + "VALUES ('" + id_group + "','"
                    + id_Bm + "')");

            stat.executeUpdate();

        } finally {
            conn.close();

        }

    }

    static public void delete_GB_BM() throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();

        try {

            PreparedStatement stat = conn.prepareStatement(
                    " DELETE FROM  `bookmark_group` "
                    + "WHERE `id_bookmark` = " + bm.getId());

            stat.executeUpdate();

        } finally {
            conn.close();

        }

    }

    static public int getIdGbFromBm(int id_bm) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT id_group "
                    + "FROM bookmark_group "
                    + "WHERE id_bookmark = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_bm);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id_group = rs.getInt(1);

            }

        } finally {
            conn.close();

        }
        return id_group;
    }

    static public int getIdGb(int id) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT id_group "
                    + "FROM bookmark_group "
                    + "WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id_group = rs.getInt(1);

            }

        } finally {
            conn.close();

        }
        return id_group;
    }

    static public int getIdBmFromGb(int id_gb) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT id_bookmark "
                    + "FROM bookmark_group "
                    + "WHERE id_group = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_gb);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id_Bm = rs.getInt(1);

            }

        } finally {
            conn.close();

        }
        return id_Bm;
    }

    static public int getIdBm(int id) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT id_bookmark "
                    + "FROM bookmark_group "
                    + "WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id_Bm = rs.getInt(1);

            }

        } finally {
            conn.close();

        }
        return id_Bm;
    }

    static public String getNameGb(int id_bm) throws SQLException {

        String name = "";
        int id_gb = getIdGb(id_bm);
        name = G_GB.getGBName(id_gb);
        return name;
    }

    static public int getId(int id_gp, int id_bm) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT * "
                    + "FROM bookmark_group "
                    + "WHERE id_bookmark = ? AND id_group = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_bm);
            ps.setInt(2, id_gp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                id_group = rs.getInt(2);
                id_Bm = rs.getInt(3);

            }

        } finally {
            conn.close();

        }
        return id;
    }

    static public void editBmGroup(int id_newGroup, int id_oldGroup, int id_bm) throws SQLException {

        Connection conn = SimpleDataSource.getConnection();

        try {
            int id_ta = getId(id_oldGroup, id_bm);
            PreparedStatement stat = conn.prepareStatement(
                    "UPDATE `bookmark_group` "
                    + "SET `id_group` = '" + id_newGroup + "' "
                    + "WHERE `bookmark_group`.`id` = " + id_ta);

            stat.executeUpdate();

        } finally {
            conn.close();

        }
    }

    static public Groupbook getBmFromGb(int id_group) throws SQLException {
        Groupbook list_bm = new Groupbook();
        list_bm.setId(id_group);
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query = "SELECT id_bookmark "
                    + "FROM bookmark_group "
                    + "WHERE id_group = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_group);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_bm = rs.getInt(1);

                bm = G_BM.getBm(id_bm);
                list_bm.addBookmark(bm);
            }

        } finally {
            conn.close();

        }
        return list_bm;
    }
}
