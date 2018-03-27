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
 * @author olivi
 */
public class G_User {

    User user = new User();

    private void CreateAccount(MouseEvent event, String mdp)
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
        conn.setAutoCommit(false);
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
    void deleteUser()
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " DELETE FROM  `user` "
                    + "WHERE `user`.`id` = " + user.getId());

            stat.executeUpdate();
        } finally {
            conn.close();
        }
    }




    

    void createUser(String mdp)
            throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `User` (`user_name`, `user_adress`,`user_password`) "
                    + "VALUES ('" + user.getNom() + "','"
                    + user.getCourriel() + "','"
                    + mdp + "')");

            stat.executeUpdate();
        } finally {
            conn.close();
        }
    }

   public static int getUserId(String name)
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
                Gestionnaire.setUsagerActif(new User(u_id));
                return rs.getInt(1);

            }

        } finally {
            conn.close();

        }
        return 0;

    }
   
   
     public static String getUserName(int id)
            throws SQLException, IOException {
 String name = "" ;
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query3 = "SELECT user_name "
                    + "FROM user "
                    + "WHERE id = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setInt(1, id);

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {
                
                 name = rs.getString(1);
                Gestionnaire.getUsagerActif().setNom(name);
                return name;

            }

        } finally {
            conn.close();

        }
        return name;

    }
    
      
    /* @J-A_edits sfdjkhskfjhsdjkh */

    
   
}
