/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import applicationclass.G_Tag;
import applicationclass.Tag;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sqlclass.SimpleDataSource;

/**
 * FXML Controller class
 * 
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class AjoutTag_v2Controller extends main_controller  implements Initializable{

    @FXML
    private TextField tag_name;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnAjouter;
    @FXML
    private TextField tag_description;
    
    
    static Stage prevStage;
    static int _id_user;
    static int _id_tag = 0;
    static int _id_bookmark;
    
  /**
   *
   */
  public Tag tag;

    /**
     * Initializes the controller class.
   * @param url
   * @param rb
     */
    @Override
   
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    
     /**
     * retourne l'id du tag selon son nom
   * @param name
   * @return 
   * @throws java.sql.SQLException
     */
    public int getTagId(String name)
            throws SQLException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            String query3 = "SELECT id "
                    + "FROM tag "
                    + "WHERE nom = ?";
            PreparedStatement ps3 = conn.prepareStatement(query3);
            ps3.setString(1, name);

            ResultSet rs = ps3.executeQuery();

            if (rs.next()) {
                _id_user = rs.getInt(1);
            }

        } finally {
            conn.close();

        }
        return _id_user;

    }

    @FXML
      /**
     * ajoute un tag a la liste
     */
    private void addTag(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

        G_Tag.setTag(tag_name.getText(),tag_description.getText());
       G_Tag.addTag();
       Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
        stageTheLabelBelongs.hide();
    }
     /**
     * ajoute un tag a un bookmark
     */
    private void addBookTag() throws IOException, SQLException, ClassNotFoundException {

        Connection conn = SimpleDataSource.getConnection();
        int type = 0;
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `bookmark_tag` ( `id_bookmark`,`id_tag`) "
                    + "VALUES ('" + _id_bookmark + "','"
                    + getTagId(tag_name.getText()) + "')");

            stat.executeUpdate();
       

        } finally {
            conn.close();
        }
    }

    @FXML
    private void exitPage(MouseEvent event) {
    }

}
