/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.G_GB;
import applicationclass.G_User;
import applicationclass.G_Validation;
import sqlclass.SimpleDataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class NouveauCompte_v1Controller extends main_controller implements Initializable {

  @FXML
  private TextField userName;
  @FXML
  private TextField userAdress;
  @FXML
  private PasswordField userPassword;
  @FXML
  private PasswordField userConfirmPassword;
  @FXML
  private Button btnAnnuler;
  @FXML
  private Button btnCreateAccount;

  static Stage prevStage;
  static String _userName;

  /**
   *
   * @param stage
   * @param userName
   */
  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    userName.setText(_userName);

  }

  @FXML
  private void CreateAccount(MouseEvent event) {
    try {
      if (Valid() && validUser()) {
        Connection conn = SimpleDataSource.getConnection();
        try {

          PreparedStatement stat = conn.prepareStatement(
                  " INSERT INTO `User` (`user_name`, `user_adress`,`user_password`) "
                  + "VALUES ('" + userName.getText() + "','"
                  + userAdress.getText() + "','"
                  + userPassword.getText() + "')");

          stat.executeUpdate();

          PreparedStatement stat2 = conn.prepareStatement(
                  " INSERT INTO `group_book` (`nom`, `Description`) "
                  + "VALUES ('" + userName.getText() + "','"
                  + "Default Group" + "')");

          stat2.executeUpdate();
          System.exit(0);

        } finally {
          conn.close();
        }
      }
    } catch (SQLException e) {
      g.addMessageErreur("La connexion dans la BD ne s'est pas produite complètement.");
    } finally {
      if (g.estEnErreur()) {
        super.showAlert();
      }
    }
    
  }
  
  private boolean addErreur(String message) {
    g.addMessageErreur(message);
    return false;
  }

  private boolean Valid() {
    boolean invalide = true;
    
    if (userName.getText().isEmpty()) {
      invalide = addErreur("Le champ du nom du compte est vide.");
    } else if (!G_Validation.nom(userName.getText())) {
      invalide = addErreur("Le nom du compte saisie n'est pas valide.");
    }
    
    if (userAdress.getText().isEmpty()) {
      invalide = addErreur("Le champ du mot de passe est vide.");
    } else if (!G_Validation.courriel(userAdress.getText())) {
      invalide = addErreur("L'adresse courriel saisie n'est pas valide.");
    }
    
    if (userPassword.getText().isEmpty()) {
      invalide = addErreur("Le champ du mot de passe est vide.");
    } else if (!G_Validation.mdp(userPassword.getText())) {
      invalide = addErreur("Le mot de passe saisie n'est pas valide.");
    } else if (!userConfirmPassword.getText().equals(userPassword.getText())) {
      invalide = addErreur("La resaisie du mot de passe n'est pas identique au premier.");
    }
    
    return invalide;
  }

  /*boolean validUser() throws IOException, SQLException, ClassNotFoundException {
    Connection conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              "(SELECT Id FROM user WHERE user_name = '" + userName.getText() + "')");

      int valid = stat.executeUpdate();

      if (valid != 0) {
        showAlert("nom de user déja pris, user ");
        return false;
      }

    } finally {
      conn.close();
    }

    return true;
  }*/

  /**
   * 
   * @throws Exception 
   */
  public void startPage() throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Connexion_v1.fxml"));

    Scene scene = new Scene(root);
    Stage secondStage = new Stage();
    secondStage.setScene(scene);
    secondStage.show();

  }

  @FXML
  private void exitPage(MouseEvent event) throws Exception {
    super.exitPage(btnAnnuler);
  }

}
