/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.G_GB;
import applicationclass.G_Validation;
import applicationclass.TA_User_GB;
import applicationclass.User;
import static controllerclass.main_controller.user;
import sqlclass.SimpleDataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller classe pour l'interface d'ajout de partage
 *
 * @author Jean-Alain Sainton
 * @author Olivier Lemay Dostie
 * @version 1.0
 */
public class AjoutPartage_v1Controller extends main_controller implements Initializable {

    /**
     * Composantes de l'interface
     */
    String groupBook;
    @FXML
    private TextField user_name;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnAjouter;
    @FXML
    private RadioButton chkConsult;
    @FXML
    private RadioButton chkCollaborateur;

    /**
     * Attributs de l'interfaces
     */
    static Stage prevStage;
    static int _id_user = 0;
    static int _id_GB;
    @FXML
    private TextField txt_groupe;

    /**
     * Initializes the controller class.
     *
     * @param stage
     * @param id_bookMark
     */
    public void setPrevStage(Stage stage, int _id_GB) {
        prevStage = stage;
        this._id_GB = _id_GB;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String name = G_GB.getGBName(_id_GB);
            txt_groupe.setText(name);
        } catch (SQLException ex) {
            Logger.getLogger(AjoutPartage_v1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addUserGroup(MouseEvent event) throws IOException, SQLException, ClassNotFoundException, Exception {

        int userId = User.getUserId(user_name.getText());
        if (userId > 0 && userId != g.getUsagerActif().getId()) {

            int id_gb = G_GB.getGBDefaultFromUser(g.getUsagerActif().getNom());

            TA_User_GB.addUserGroup(userId, id_gb);

            Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
            stageTheLabelBelongs.hide();
        }

    }
}