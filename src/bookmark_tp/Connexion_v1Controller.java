/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moi
 */
public class Connexion_v1Controller extends Application implements Initializable {

    Stage stageTheLabelBelongs;
    @FXML
    private TextField user_name;
    @FXML
    private TextField user_password;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnNewAccount;

    Stage primaryStage;
    int _id_user;

    /**
     * Initializes the controller class.
     */
    public void setPrevStage(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

// void hide() throws IOException
//    {
//     Parent root = FXMLLoader.load(getClass().getResource("Connexion_v1.fxml"));
//        
//        Scene scene = new Scene(root);
//        
//        primaryStage.setScene(scene);}
    @FXML
    private void newAccount() throws Exception {

        NouveauCompte_v1Controller controller = new NouveauCompte_v1Controller();
        Stage stageTheLabelBelongs = (Stage) btnConnect.getScene().getWindow();

        controller.setPrevStage(stageTheLabelBelongs, user_name.getText());

        Parent root = FXMLLoader.load(getClass().getResource("NouveauCompte_v1.fxml"));

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();

    }

    boolean validUser() throws IOException, SQLException, ClassNotFoundException {
        try (Connection conn = SimpleDataSource.getConnection()) {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT Id FROM user WHERE user_name = '" + user_name.getText() + "')");

            int valid = stat.executeUpdate();

            if (valid == 0) {
                showAlert("nom de user ");
                return false;
            }

        }
        return true;
    }

    boolean validPassword() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT user_password FROM user WHERE user.user_name = '" + user_name.getText() + "')");

            ResultSet rs = stat.executeQuery();
            String pass = null;
            String pass2 = user_password.getText();

            if (rs.next()) {
                pass = rs.getString(1);
                if (!pass.equals(pass2)) {
                    showAlert("mot de passe ");
                    return false;
                }
            }

        } finally {
            conn.close();

        }

        return true;
    }

    void showAlert(String var) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(var + " invalide");

        alert.showAndWait();
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

    boolean validContent() {
        if (user_name.getText().isEmpty()) {
            showAlert("Champs name Vide et ");
            return false;
        } else if (user_password.getText().isEmpty()) {
            showAlert("Champs password Vide et ");
            return false;
        }
        return true;
    }

    @FXML
    private void connectUser(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (validContent() == true && validUser() == true && validPassword() == true) {

            ajoutTag_v1Controller controller = new ajoutTag_v1Controller();
            Stage stageTheLabelBelongs = (Stage) btnConnect.getScene().getWindow();

          
            Parent root = FXMLLoader.load(getClass().getResource("ajout_tag_v1.fxml"));

            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            
            controller.setPrevStage(stageTheLabelBelongs, 1, getUserId(user_name.getText()));
            secondStage.setScene(scene);
            
            stageTheLabelBelongs.hide();
            secondStage.show();
        }
    }

}
