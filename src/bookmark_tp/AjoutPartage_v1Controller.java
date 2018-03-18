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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moi
 */
public class AjoutPartage_v1Controller implements Initializable {

    String groupBook;
    @FXML
    private TextField user_name;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnAjouter;
    @FXML
    private RadioButton chkConsult;
    @FXML
    private RadioButton chkCollaborateur;
    static Stage prevStage;
    static int _id_user = 0;
    static int _id_bookMark;

    /**
     * Initializes the controller class.
     */
    public void setPrevStage(Stage stage, int id_bookMark) {
        prevStage = stage;
        _id_bookMark = id_bookMark;
    }

    private void exitPage(MouseEvent event) throws Exception {

        Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
        stageTheLabelBelongs.hide();
        prevStage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void showAlert(String var) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(var + " invalide");

        alert.showAndWait();
    }

    boolean validUser() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            _id_user = getUserId(user_name.getText());

            if (_id_user == 0) {
                showAlert("nom de user ");
                return false;
            }

            PreparedStatement stat2 = conn.prepareStatement(
                    "(SELECT Id FROM user_group WHERE id_user = '" + _id_user + "')");

            int valid = stat2.executeUpdate();

            if (valid != 0) {
                showAlert("User déja lier, ajout  ");
                return false;
            }

        } finally {
            conn.close();

        }
        return true;
    }

    @FXML
    private void closeStage(MouseEvent event) {

        Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
        stageTheLabelBelongs.hide();
        prevStage.show();
    }

    @FXML
    private void addUserGroup(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (validUser()) {
           
            int type = 0;
            Connection conn = SimpleDataSource.getConnection();
            
            try {
                if (chkConsult.isSelected()) {
                    type = 1;
                }
                if (chkCollaborateur.isSelected()) {
                    type = 2;
                }
                
                PreparedStatement stat = conn.prepareStatement(
                        " INSERT INTO `user_group` (`id_type`, `id_user`,`id_groupBook`) "
                        + "VALUES ('" + type + "','"
                        + _id_user + "','"
                        + _id_bookMark + "')");

                stat.executeUpdate();
                System.exit(0);

            } finally {
                conn.close();
            }
        }

    }

    public int getUserId(String name)
            throws SQLException {

        Connection conn = SimpleDataSource.getConnection();
        try {

            String query3 = "SELECT id "
                    + "FROM user "
                    + "WHERE user_name = ?";
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
}
