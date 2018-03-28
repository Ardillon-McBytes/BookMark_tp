/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.G_Tag;
import applicationclass.Tag;
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
 * FXML Classe du Controlleur pour l'ajout d'un tag
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class AjoutTag_v2Controller extends main_controller implements Initializable {

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

  
  @FXML
  /**
   * ajoute un tag a la liste
   */
  private void addTag(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

    G_Tag.setTag(tag_name.getText(), tag_description.getText());
      if (G_Tag.getTagFromName(tag_name.getText()).getId() < 1) {
          G_Tag.addTag();
      }
  
    Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
    stageTheLabelBelongs.hide();
  }

  /**
   * ajoute un tag a un bookmark
   */

  @FXML
  private void exitPage(MouseEvent event) {
     Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
    stageTheLabelBelongs.hide();
  }

}
