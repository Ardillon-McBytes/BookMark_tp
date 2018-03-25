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
 * @author olivi
 */
public class G_BM {
  Bookmark bm;
  
  
 public G_BM()
  {}
  
  Bookmark getBookMark()
  {
  
  return bm;
  }
  
  public void setBookMark(Bookmark bm)
  {
  
  this.bm = bm;
  }
  

  
public   void getBm(int id) throws SQLException
  {             
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
  public void addBm() throws SQLException
  {             
       Connection conn = SimpleDataSource.getConnection();
            
            try {
               
                
                PreparedStatement stat = conn.prepareStatement(
                        " INSERT INTO `bookmark` (`nom_site`, `Description`,`Url`) "
                        + "VALUES ('" + bm.getNom() + "','"
                        + bm.getDescription() + "','"
                        + bm.getUrl() +   "')");
                
                stat.executeUpdate();
           
                
            } finally {
                conn.close();
            



    }
  }
    public void editBm() throws SQLException
  {             
       Connection conn = SimpleDataSource.getConnection();
            
            try {
               
                
                PreparedStatement stat = conn.prepareStatement(
                        " UPDATE `bookmark` "
                        + "SET 'nom_site' = '" + bm.getNom() + "','"
                        +" 'Description = '" + bm.getDescription() + "','"
                        +  " 'Url = '" + bm.getUrl() +   "'"
                 + "WHERE 'bookmark'.'id' = " + bm.getId() );
                
                stat.executeUpdate();
             
                
            } finally {
                conn.close();
            



    }
  }
}
