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
 * FXML Classe du Controlleur parente à tous les autres controlleurs
 * 
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class main_controller {

  /**
   * Stage précédent pour la navigation entre elles
   */
  protected static Stage previousStage;
  /*@old-node_suggestion*/

  /**
   * Gestionnaire statique principale accessible par tout les autres controleurs
   */
  protected static Gestionnaire g = new Gestionnaire();

  /**
   * Attribut utilisateur pour faire certaines gestions
   */
  protected static User user = new User();

  /**
   * Valide si les informations de l'utilisateurs enregistré dans le gestionnaire sont valides
   * 
   * @return Vrai si le nom de l'utilisateur est présent dans la BD
   */
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

  /**
   * Change de stage par celui précédent
   * 
   * @param stage 
   */
  void setPrevStage(Stage stage) {
    prevStage = stage;
  }

  /**
   * Procède à la fermeture de la page active
   * 
   * @param object 
   * @throws Exception 
   */
  void exitPage(Object object) throws Exception {

    Stage stageTheLabelBelongs = (Stage) ((Node) object).getScene().getWindow();
    stageTheLabelBelongs.hide();
    prevStage.show();

  }

  /**
   * Affiche les messages du gestionnaire
   */
  void showMessages() {
    if (g.estEnErreur()) {
      showAlert();
    } else if (g.aUneConfirmation()) {
      showConfirmation();
    }
  }

  /**
   * Affiche des messages 
   * 
   * @param var Message à afficher
   */
  void showMessages(String var) {
    if (g.estEnErreur()) {
      showAlert(var);
    } else {
      showConfirmation(var);
    }
  }

  /**
   * Affiche les messages de confirmation du gestionnaire
   */
  void showConfirmation() {
    showConfirmation(g.getMessageConfirmation());
  }

  /**
   * Affiche un message de confirmation
   * 
   * @param var Message de confirmation
   */
  void showConfirmation(String var) {
    showMessage(Alert.AlertType.CONFIRMATION, "Confirmation", null, var);
  }

  /**
   * Affiche les messages d'erreurs du gestionnaire
   */
  void showAlert() {
    showAlert(g.getMessageErreur());
    g.estEnErreur(false);
  }

  /**
   * Affiche des messages d'erreurs
   * 
   * @param var Message d'erreur
   */
  void showAlert(String var) {
    showMessage(Alert.AlertType.ERROR, "Error", null,
            "Une ou plusieurs erreurs se sont produites. Les messages retenus sont : \n\n" + var);
  }

  /**
   * Procède à la création de la fenêtre d'affichage qui affiche les messages voulues
   * 
   * @param type Type de l'alerte de la fenêtre
   * @param titre Titre de la fenêtre
   * @param header Message suppérieur de la fenêtre
   * @param content Message à afficher
   */
  private void showMessage(Alert.AlertType type, String titre, String header, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(titre);
    alert.setHeaderText(header);
    alert.setContentText(content);

    alert.showAndWait();
  }

  /*@old-node_suggestion_start en construction*/
  /**
   * Obtient le stage précédent
   * 
   * @return Le stage précédent
   */
  Stage getPreviousStage() {
    return previousStage;
  }

  /*@old-node_suggestion Inverser la méthode pour qu'elle recherche le contrôleur correspondant à l'interface choisie*/
  /**
   * Méthode qui permet d'alterner la fenêtre vers une autre à l'aide du nom du controlleur
   * 
   * @param controllerFileName Nom du fichier du contrôleur ciblé
   */
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
      g.addMessageErreur("Le format du nom des fichiers n'est pas suffisant.");
    }
  }
  /*@old-node_suggestion_end*/
}
