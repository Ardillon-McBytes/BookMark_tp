/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import applicationclass.Gestionnaire;
import applicationclass.User;
import static controllerclass.PagePrincipaleController.prevStage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sqlclass.SimpleDataSource;

/**
 *
 * @author moi
 */
public class main_controller {
  static Gestionnaire g = new Gestionnaire();
  static User user = new User();

  boolean validUser() throws IOException, SQLException, ClassNotFoundException {
    {

      if (g.getUsagerActif().getUserId(g.getUsagerActif().getNom()) < 1) {
        showAlert("nom de user invalide");
        return false;
      }

    }
    return true;
  }

  public int getUserId(User user) throws SQLException, IOException {
    user.getUserId(user.getNom());
    g.setUsagerActif(user);
    return user.getId();
  }

  void setPrevStage(Stage stage) {

    prevStage = stage;

  }

  void exitPage(Object object) throws Exception {

    Stage stageTheLabelBelongs = (Stage) ((Node) object).getScene().getWindow();
    stageTheLabelBelongs.hide();
    prevStage.show();

  }

  void showAlert(String var) {
    
    Alert alert = new Alert(Alert.AlertType.ERROR);
    Alert.AlertType.valueOf(Gestionnaire.getMessageErreur());
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(var);

    alert.showAndWait();
  }
}
