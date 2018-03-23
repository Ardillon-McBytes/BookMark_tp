/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class AjoutTag_v2Controller extends main_controller  implements Initializable {

  @FXML
  private TextField tag_name;
  @FXML
  private Button btnAnnuler;
  @FXML
  private Button btnAjouter;
  @FXML
  private TextField tag_description;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void exitPage(MouseEvent event) {
  }

  @FXML
  private void addTag(MouseEvent event) {
  }
  
}
