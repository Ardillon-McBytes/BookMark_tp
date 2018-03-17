/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
    private Stage primaryStage;

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
        controller.setPrevStage(stageTheLabelBelongs);
        stageTheLabelBelongs.hide();

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
        try (Connection conn = SimpleDataSource.getConnection()) {

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

    @FXML
    private void connectUser(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
      
        if (validContent() ==true && validUser() == true && validPassword() == true) {
            Parent root = FXMLLoader.load(getClass().getResource("ajoutPartage_v1.fxml"));

            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.show();
        }
    }
    
    boolean validContent()
    {
        if (user_name.getText().isEmpty()) {
             showAlert("Champs name Vide et ");
             return false;
        }
        else  if (user_password.getText().isEmpty()) {
             showAlert("Champs password Vide et ");
             return false;
        }
    return true;
    }
}
