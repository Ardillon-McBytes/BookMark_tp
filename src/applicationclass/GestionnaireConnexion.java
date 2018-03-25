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
import sqlclass.SimpleDataSource;

/**
 *
 * @author moi
 */
public class GestionnaireConnexion {

    User user;

    
    boolean validUser() throws IOException, SQLException, ClassNotFoundException
    {
        if (validName() == true &&
                validPassword() == true) {
            return true;
            
        }
        return false;
    }
    boolean validName() {
        if (user.getId() > 0) {
            return true;
        }
        return false;

    }

    boolean validPassword() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT user_password "
                    + "FROM user "
                    + "WHERE user.user_name = '" + user.getNom() + "')");

            ResultSet rs = stat.executeQuery();
            String pass = null;
            String pass2 = user.getMdp();

            if (rs.next()) {
                pass = rs.getString(1);
                if (!pass.equals(pass2)) {
                    return false;
               
                }
            }

        } finally {
            conn.close();

        }

        return true;
    }
}
