/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.Gestionnaire;
import applicationclass.User;
import static controllerclass.PagePrincipaleController.prevStage;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import sqlclass.SimpleDataSource;

/**
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class main_controller {

  /**
   *
   */
  protected static Stage previousStage;
  /*@old-node_suggestion*/

  /**
   *
   */
  protected static Gestionnaire g = new Gestionnaire();

  /**
   *
   */
  protected static User user = new User();

  boolean validUser() {
    {
      try {
        if (g.getUsagerActif().getUserId(g.getUsagerActif().getNom()) < 1) {
          g.addMessageErreur("Le nom de l'utilisateur n'est pas dans la BD.");
          return false;
        }
      } catch (IOException e) {
        g.addMessageErreur("Les champs saisies ne sont pas valide");
        return false;
      } catch (SQLException e) {
        g.addMessageErreur("Il est impossible de se connecter à la BD.");
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

  void showMessages() {
    if (g.estEnErreur()) {
      showAlert();
    } else {
      showConfirmation();
    }
  }

  void showMessages(String var) {
    if (g.estEnErreur()) {
      showAlert(var);
    } else {
      showConfirmation(var);
    }
  }

  void showConfirmation() {
    showConfirmation(g.getMessageConfirmation());
  }

  void showConfirmation(String var) {
    showMessage(Alert.AlertType.CONFIRMATION, "Confirmation", null, var);
  }

  void showAlert() {
    showAlert(g.getMessageErreur());
  }

  void showAlert(String var) {
    showMessage(Alert.AlertType.ERROR, "Error", null,
            "Une ou plusieurs erreurs se sont produites. Les messages retenus sont : \n\n" + var);
    g.estEnErreur(false);
  }

  private void showMessage(Alert.AlertType type, String titre, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(titre);
    alert.setHeaderText(header);
    alert.setContentText(content);

    alert.showAndWait();
  }

  /*boolean showConfirmation() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Your Message", "Title on Box", dialogButton);
        
        if (dialogResult == 0) {
          return true;
        } else {
            return false;
        }
    }*/
 /*@old-node_suggestion_start en construction*/
  Stage getPreviousStage() {
    return previousStage;
  }

  /*@old-node_suggestion Inverser la méthode pour qu'elle recherche le contrôleur correspondant à l'interface choisie*/
  void goToInteface(String controllerFileName/*, ElementInterface elem*/) {
    try {
      main_controller objectif;
      String fichierInterface = null;
      int elem; // test
      Stage stageTheElemBelongs = new Stage();
      //stageTheElemBelongs = (Stage) elem.getScene().getWindow();

      /**
       * @old-node_question Il est possible d'envoyer en paramètre une classe de
       * l'application, mais comment l'utiliser pour instancier des objets?
       */
      switch (controllerFileName) {
        case "AjoutBm_v1Controller":
          objectif = new AjoutBm_v1Controller();
          fichierInterface = "ajout_bm_v1";
          ((AjoutBm_v1Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "AjoutPartage_v1Controller":
          objectif = new AjoutPartage_v1Controller();
          fichierInterface = "ajoutPartage_v1";
          ((AjoutPartage_v1Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "AjoutTag_v2Controller":
          objectif = new AjoutTag_v2Controller();
          fichierInterface = "ajout_tag_v1";
          ((AjoutTag_v2Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "Connexion_v1Controller":
          objectif = new Connexion_v1Controller();
          fichierInterface = "connexion_v1";
          ((Connexion_v1Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "InfoPartage_v1Controller":
          objectif = new InfoPartage_v1Controller();
          fichierInterface = "infoPartage_v1";
          ((InfoPartage_v1Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "NouveauCompte_v1Controller":
          objectif = new NouveauCompte_v1Controller();
          fichierInterface = "nouveauCompte_v1";
          ((NouveauCompte_v1Controller) objectif).setPrevStage(stageTheElemBelongs);
          break;
        case "PagePrincipaleController":
          objectif = new PagePrincipaleController();
          fichierInterface = "pagePrincipale_v2";
          ((PagePrincipaleController) objectif).setPrevStage(stageTheElemBelongs);
          break;
        default:
          g.addMessageErreur("Le nom du controlleur reçu ne correspond à aucune classe de l'application.");
          break;
      }

      Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/" + fichierInterface + ".fxml"));

      Scene scene = new Scene(root);
      Stage secondStage = new Stage();

      secondStage.setScene(scene);

      stageTheElemBelongs.hide();
      secondStage.show();

    } catch (IOException e) {
      g.addMessageErreur("Mauvais format pour le nom de l'utilisateur");
    }
  }
  /*@old-node_suggestion_end*/
}
