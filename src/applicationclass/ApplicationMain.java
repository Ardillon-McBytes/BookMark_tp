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
 * Classe du programme principal?
 * (Il faudrait qu'on développe un gestionnaire qui 
 * facilite les modifications via les TAs)
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.1
 */
public class ApplicationMain extends Application {

  private Stage primaryStage;
  
  /**
   * @param args the command line arguments
   * @throws java.lang.Exception
   */
  public static void main(String[] args) throws Exception {

      SimpleDataSource.init("database.properties");
      launch(args);
  }

  /**
   * 
   * @param primaryStage
   */
  public void setPrimaryStage(Stage primaryStage) {
      this.primaryStage = primaryStage;
  }

  /**
   * 
   * @return
   */
  public Stage getPrimaryStage() {
      return this.primaryStage;
  }

  @Override
  public void start(Stage stage) throws Exception {

      Parent root = FXMLLoader.load(getClass().getResource("Connexion_v1.fxml"));

      Connexion_v1Controller controll = new Connexion_v1Controller();
      controll.setPrevStage(stage);

      Scene scene = new Scene(root);
      Stage secondStage = new Stage();

      secondStage.setScene(scene);
      secondStage.show();
  }
}
