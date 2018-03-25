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
public class G_Tag {
    
    Tag tag; 
    
    Tag getTag()
  {
  
  return tag;
  }
  
  void setTag(Bookmark bm)
  {
  
  this.tag = tag;
  }
  
  
  
   void getTagInfo(int id) throws SQLException
  {             
        Connection conn = SimpleDataSource.getConnection();
        try {

            String query3 = "SELECT * "
                    + "FROM tag "
                    + "WHERE id = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setInt(1, id);

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {
      
              tag.setId(rs.getInt(1));       
              tag.setNom(rs.getString(2));
              tag.setDescription(rs.getString(3));
            }

        } finally {
            conn.close();



    }
  }
   void addTag() throws SQLException
  {             
       Connection conn = SimpleDataSource.getConnection();
            
            try {
               
                
                PreparedStatement stat = conn.prepareStatement(
                        " INSERT INTO `tag` (`nom`, `description`) "
                        + "VALUES ('" + tag.getNom() + "','"
                        + tag.getDescription() +   "')");
                
                stat.executeUpdate();
                System.exit(0);
                
            } finally {
                conn.close();
            



    }
  }
    void editTag() throws SQLException
  {             
       Connection conn = SimpleDataSource.getConnection();
            
            try {
               
                
                PreparedStatement stat = conn.prepareStatement(
                        " UPDATE `tag` "
                        + "SET 'nom' = '" + tag.getNom() + "','"
                        +" 'description = '" + tag.getDescription()  +   "'"
                 + "WHERE 'tag'.'id' = " + tag.getId() );
                
                stat.executeUpdate();
                System.exit(0);
                
            } finally {
                conn.close();
    }
  }
}
