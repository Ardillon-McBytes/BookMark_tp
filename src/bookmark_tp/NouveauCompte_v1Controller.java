/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author moi
 */
public class NouveauCompte_v1Controller implements Initializable {

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
    private void CreateAccount(MouseEvent event) {
    }
    
}
