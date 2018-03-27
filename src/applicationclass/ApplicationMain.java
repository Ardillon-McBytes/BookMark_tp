/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import controllerclass.Connexion_v1Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sqlclass.SimpleDataSource;

/**
 * Classe du programme principal
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.1
 */
public class ApplicationMain extends Application {

  private Stage primaryStage;

  /**
   * Méthode de démarage de l'application
   *
   * @param args Le fichier de configuration utilisé pour la connexion à la BD
   * @throws java.lang.Exception Erreur du programme à son départ
   */
  public static void main(String[] args) throws Exception {

    SimpleDataSource.init("database.properties");
    launch(args);
  }

  /**
   * Change le Stage principal
   *
   * @param primaryStage Le nouveau Stage
   */
  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  /**
   * Obtient le Stage principal
   *
   * @return Le Stage principal
   */
  public Stage getPrimaryStage() {
    return this.primaryStage;
  }

  /**
   * Méthode contrale de l'application
   *
   * @param stage Stage utilisé
   * @throws Exception Erreur de l'application lors de son exécution
   */
  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/connexion_v1.fxml"));

    Connexion_v1Controller controll = new Connexion_v1Controller();
    controll.setPrevStage(stage);

    Scene scene = new Scene(root);
    Stage secondStage = new Stage();

    secondStage.setScene(scene);
    secondStage.show();
  }
}
