/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

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

/**
 * FXML Controller class
 *
 * @author moi
 */
public class ajoutTag_v1Controller implements Initializable {

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
     * Initializes the controller class.
     */
    @Override
   
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
      /**
     go to previous page
     */
    private void exitPage(MouseEvent event) {
        Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
        stageTheLabelBelongs.hide();
        prevStage.show();
    }

    @FXML
     /**
     * get info from previous stage
     */
    public void setPrevStage(Stage stage, int id_bookMark, int id_user) {
        prevStage = stage;
        _id_user = id_user;
        _id_bookmark = id_bookMark;
    }

    @FXML
     /**
     * show Alert
     */
    void showAlert(String var) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(var);

        alert.showAndWait();
    }

    
     /**
     * retourne l'id du tag selon son nom
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

        Connection conn = SimpleDataSource.getConnection();

      
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `tag` ( `nom`,`description`) "
                    + "VALUES ('" + tag_name.getText() + "','"
                    + tag_description.getText() + "')");

            stat.executeUpdate();

        } finally {

            conn.close();
        }

        addBookTag();
        showAlert("Done");
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
            System.exit(0);

        } finally {
            conn.close();
        }
    }

}