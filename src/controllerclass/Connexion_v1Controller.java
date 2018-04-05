/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.G_User;
import applicationclass.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller classe pour l'interface de connexion
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
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
    @Override
    public void setPrevStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     *
     * @param primaryStage
     */
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
//        primaryStage.setScene(scene);
//    }
    @FXML
    private void newAccount() throws Exception {

        NouveauCompte_v1Controller controller = new NouveauCompte_v1Controller();
        stageTheLabelBelongs = (Stage) btnConnect.getScene().getWindow();

        controller.setPrevStage(stageTheLabelBelongs);
        stageTheLabelBelongs.hide();
        Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/nouveauCompte_v1.fxml"));

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();

    }

    @FXML
    private void connectUser(MouseEvent event) throws SQLException, IOException {

        g.getUsagerActif().setNom(user_name.getText());
        G_User.setUser(new User(user_name.getText()));

        try {
            if (validContent() && G_User.validUser(user_password.getText())) {
                g.getUsagerActif().setId(G_User.getUserId(user_name.getText()).getId());

                PagePrincipaleController controller = new PagePrincipaleController();
                stageTheLabelBelongs = (Stage) btnConnect.getScene().getWindow();
                controller.setPrevStage(stageTheLabelBelongs);

                Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/pagePrincipale_v2.fxml"));

                Scene scene = new Scene(root);
                Stage secondStage = new Stage();

                secondStage.setScene(scene);

                stageTheLabelBelongs.hide();
                secondStage.show();

            }
        } catch (SQLException e) {
            g.addMessageErreur("La connexion dans la BD ne s'est pas produite complètement.");
        } catch (IOException e) {
            /**
             * @old-node_question Est-ce qu'on devrait plutôt ajouter le message
             * suivant dans avec la méthode addMessageConfirmation ?
             */
            g.addMessageErreur("Un ou plusieurs champs reçues ne sont pas valide pour le fonctionnement du programme.");
        } catch (ClassNotFoundException e) {
            g.addMessageErreur("Des dépendances du programme n'ont pas été correctement inclues.");
        } catch (Exception e) {
            g.addMessageErreur("Une erreur non répertoriée s'est produite.");
        } finally {
            if (g.estEnErreur()) {
                super.showAlert();
            }
        }
    }

    /**
     * Valide si les champs de l'interface est conforme pour la création des
     *
     * @param name
     * @return
     * @throws SQLException Connexion à la base de donnée incomplète
     */
    boolean validContent() {
        boolean etat = true;
        if (user_name.getText().isEmpty()) {
            g.addMessageErreur("Le champ du nom est vide");
            etat = false;
        }
        if (user_password.getText().isEmpty()) {
            g.addMessageErreur("Le champ du mot de passe est vide");
            etat = false;
        }
        return etat;

    }
}
