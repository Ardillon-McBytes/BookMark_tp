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
import applicationclass.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private void CreateAccount(MouseEvent event) throws IOException, ClassNotFoundException, Exception {
        try {
            if (Valid() && (G_User.getUserId(_userName).getId() < 1)) {
                G_User.setUser(new User(userName.getText(), userAdress.getText()));
                G_User.createUser(userPassword.getText());
                G_GB.createGb(user);
                
                super.exitPage(btnAnnuler);
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
    /**
     *
     * @throws Exception
     */
    @FXML
    private void exitPage(MouseEvent event) throws Exception {
        super.exitPage(btnAnnuler);
    }

}
