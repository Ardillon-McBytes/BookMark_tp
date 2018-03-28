/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.G_GB;
import applicationclass.TA_User_GB;
import static controllerclass.main_controller.g;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Classe du Controlleur pour l'ajout d'un groupbook
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class AjoutGb_v1Controller implements Initializable {

  @FXML
  private TextField txt_nom_gb;
  @FXML
  private Button btnAnnuler;
  @FXML
  private Button btnAjouter;
  @FXML
  private TextField txt_description;

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void exitPage(MouseEvent event) {

    Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
    stageTheLabelBelongs.hide();
  }

  @FXML
  private void addGb(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

    int gb_id = G_GB.getGBId(txt_nom_gb.getText());
    if (gb_id < 1) {
      G_GB.createGb(txt_nom_gb.getText(), txt_description.getText());
      gb_id = G_GB.getGBId(txt_nom_gb.getText());
      TA_User_GB.addUserGroup(g.getUsagerActif().getId(), gb_id);
      Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
      stageTheLabelBelongs.hide();
    }

  }

}
