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
import javafx.scene.input.MouseEvent;
import sqlclass.SimpleDataSource;

/**
 * AKA Connexion
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_User {

    private static User user = new User();
    private static Connection conn;

    /**
     *
     * @param event
     * @param mdp
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void CreateAccount(MouseEvent event, String mdp)
            throws IOException, SQLException, ClassNotFoundException {

        conn = SimpleDataSource.getConnection();
        conn.setAutoCommit(false);
        try {

            createUser(mdp);
            G_GB.createGb(user);
            conn.commit();
        } finally {
            conn.close();
        }
    }

    static public void setUser(User user) {
        G_User.user = user;
    }

    /**
     *
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    static public void createUser(String mdp)
            throws IOException, SQLException, ClassNotFoundException {

        conn = SimpleDataSource.getConnection();
        try {

            createUser(mdp);
            G_GB.createGb(user);
            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            /* Transaction annulée */
        } finally {
            conn.close();
        }
    }

    /**
     *
     * @param mdp
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void deleteUser()
            throws IOException, SQLException, ClassNotFoundException {

        conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " DELETE FROM  `user` "
                    + "WHERE `user`.`id` = " + user.getId());

            stat.executeUpdate();
        } finally {
            conn.close();
        }
    }

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public int getUserId(User user)
            throws SQLException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            String query3 = "SELECT id "
                    + "FROM user "
                    + "WHERE user_name = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setString(1, user.getNom());

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);

            }
        } finally {
            conn.close();
        }
        /**
         *
         * @param name
         * @return
         * @throws SQLException
         * @throws IOException
         */
        return 0;
    }
    public static User getUserId(String name)
            throws SQLException, IOException {

        Connection conn = SimpleDataSource.getConnection();
        try {
            String query3 = "SELECT id "
                    + "FROM user "
                    + "WHERE user_name = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setString(1, name);

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {
                int u_id = rs.getInt(1);
                return new User(u_id);
            }
        } finally {
            conn.close();
        }
        return null;
    }

    public static String getUserName(int id)
            throws SQLException, IOException {

        Connection conn = SimpleDataSource.getConnection();
        try {
            String query3 = "SELECT user_name "
                    + "FROM user "
                    + "WHERE id = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setInt(1, id);

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {

                return rs.getString(1);
            }
        } finally {
            conn.close();
        }
        return null;
    }

    /**
     * Valide la connexion de l'utilisateur par son nom et son mot de passe
     *
     * @param mdp
     * @return Vrai si le mot de passe est le même que dans la BD et que son nom
     * est valide
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    static public boolean validUser(String mdp)
            throws IOException, SQLException, ClassNotFoundException {
        return validName() == true && validPassword(mdp) == true;
    }

    /**
     * Valide que l'identifiant est valide (après avoir recherché par son nom)
     *
     * @return Vrai si l'identifiant est valide
     */
   static  public boolean validName() throws SQLException, IOException {
        return (G_User.getUserId(user.getNom()).getId() > 0);
    }

    /**
     * Valide le mot de passe de l'utilisateur
     *
     * @param mdp
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
   static public boolean validPassword(String mdp)
            throws IOException, SQLException, ClassNotFoundException {

        conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT user_password "
                    + "FROM user "
                    + "WHERE user.user_name = '" + user.getNom() + "')");

            ResultSet rs = stat.executeQuery();
            String pass = null;

            if (rs.next()) {
                pass = rs.getString(1);
                if (!pass.equals(mdp)) {
                    return false;
                }
            }
        } finally {
            conn.close();
        }
        return true;
    }
}
