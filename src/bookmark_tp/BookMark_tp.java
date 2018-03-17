/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmark_tp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Stage;

/**
 *
 * @author moi
 */
public class BookMark_tp extends Application {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        SimpleDataSource.init("database.properties");
        launch(args);

    }

}
