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
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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

  void setPrevStage(Stage stage) {
    prevStage = stage;
  }

  void exitPage(Object object) throws Exception {

    Stage stageTheLabelBelongs = (Stage) ((Node) object).getScene().getWindow();
    stageTheLabelBelongs.hide();
    prevStage.show();

  }

  void showAlert() {
    showAlert(Gestionnaire.getMessageErreur());
  }
  
  void showAlert(String var) {
    
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(var);

    alert.showAndWait();
  }
}
