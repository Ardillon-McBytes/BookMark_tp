/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

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
     Stage prevStage;
     
     public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)   {
      
      
       
     
    }
   
  
    @FXML
    private void CreateAccount(MouseEvent event)
            throws IOException, SQLException, ClassNotFoundException  {
      if (Valid()&& validUser())
      {
        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    " INSERT INTO `User` (`user_name`, `user_adress`,`user_password`) "
                    + "VALUES ('" + userName.getText() + "','"
                    + userAdress.getText() + "','"
                    + userPassword.getText() + "')");
           userConfirmPassword.setText("allo");
            stat.executeUpdate();
            System.exit(0);
            
        } finally {
            conn.close();
        }
        }
    }
    
    private boolean Valid()
    {
        
        if (userName.getText().isEmpty()) {
            showAlert("userName");
            return false;
        }
        else  if (userAdress.getText().isEmpty()) {
            showAlert("userAdress");
             return false;
        }
        else if (userPassword.getText().isEmpty()) {
            showAlert("userPassword");
             return false;
        }
        else  if (userConfirmPassword.getText().isEmpty()) {
            showAlert("userConfirmPassword");
             return false;
        }
        else  if (!userConfirmPassword.getText().equals(userPassword.getText())) {
            showAlert("les 2 mdp ne corresponde pas. l'un des deux est ");
             return false;
        }
        
        return true;
    }
    
    void showAlert(String var)
    {
   Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error");
alert.setHeaderText(null);
alert.setContentText(var + " invalide");

alert.showAndWait();
    }
    
    
    boolean validUser ()throws IOException, SQLException, ClassNotFoundException 
    {
         Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT Id FROM user WHERE user_name = '"+userName.getText()  + "')");
                        
           int valid =  stat.executeUpdate();
           
           if (valid != 0)
           {
           showAlert("nom de user déja pris, user ");
           return false;
           }
         
            
        } finally {
            conn.close();
        }
            
        
    return true;
    }

    @FXML
    private void exitPage(MouseEvent event)throws Exception {
   
   Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
   stageTheLabelBelongs.hide();
   startPage();
  
   
    }
    public void startPage() throws Exception {
      

   Parent root = FXMLLoader.load(getClass().getResource("Connexion_v1.fxml"));
     
        Scene scene = new Scene(root);
        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.show();
    
   
    }
 
}


