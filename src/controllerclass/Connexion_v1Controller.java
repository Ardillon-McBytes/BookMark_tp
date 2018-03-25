/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import sqlclass.SimpleDataSource;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author moi
 */
public class Connexion_v1Controller extends main_controller implements Initializable {

  Stage stageTheLabelBelongs;
  @FXML
  private TextField user_name;
  @FXML
  private TextField user_password;
  @FXML
  private Button btnConnect;

  Stage primaryStage;
  int _id_user;

  /**
   * Initializes the controller class.
   *
   * @param stage
   */
  public void setPrevStage(Stage stage) {
    this.primaryStage = stage;
  }

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
    Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/nouveauCompte_v1.fxml"));

    Scene scene = new Scene(root);
    Stage secondStage = new Stage();

    secondStage.setScene(scene);
    secondStage.show();

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

  /**
   *
   * @param name
   * @return
   * @throws SQLException
   */
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

    gestionnaire.getUsagerActif().setNom(user_name.getText());

    if (validContent() == true && super.validUser() == true && validPassword() == true) {

      PagePrincipaleController controller = new PagePrincipaleController();
      Stage stageTheLabelBelongs = (Stage) btnConnect.getScene().getWindow();
      controller.setPrevStage(stageTheLabelBelongs);

      Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/pagePrincipale_v2.fxml"));

      Scene scene = new Scene(root);
      Stage secondStage = new Stage();

      secondStage.setScene(scene);

      stageTheLabelBelongs.hide();
      secondStage.show();
    }
  }

}
