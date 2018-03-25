/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.Bookmark;
import applicationclass.GestionnaireBM;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sqlclass.SimpleDataSource;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author olivi
 */
public class PagePrincipaleController extends main_controller implements Initializable {

    @FXML
    private ImageView btnGroup;
    @FXML
    private ImageView btnRemove_file;
    @FXML
    private ImageView btn_addFile;
    @FXML
    private ImageView btn_remove_bm;
    @FXML
    private ImageView btnAdd_Bm;
    @FXML
    private ImageView btnRemove_user;
    @FXML
    private ImageView btnAdd_user;
    @FXML
    private ImageView btnInfo_selected;
    @FXML
    private ImageView btnHelp;
    @FXML
    private ImageView btnRefresh;
    @FXML
    private GridPane gp_principal;
    @FXML
    private TextField txt_file_name;
    @FXML
    private TextField txt_adress;
    @FXML
    private ImageView btnAccepted;
    @FXML
    private ImageView btnInfo_tag;
    @FXML
    private ImageView btnAdd_txt_tag;

    @FXML
    private ListView<?> list_mp;
    @FXML
    private Button btn_12;
    @FXML
    private ImageView btnRefresh3;

    static Stage prevStage;
    static String _userName;
    static int _user_id;
    @FXML
    private TextField txt_tag_name;
    @FXML
    private Hyperlink hyper_removeFile;

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

    }

    void getBookMark() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = SimpleDataSource.getConnection();
        ObservableList items = FXCollections.observableArrayList();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT id_groupBook FROM user_group WHERE id_user = '" + gestionnaire.getCurrentUser().getId() + "')");

            ResultSet rs = stat.executeQuery();
            int id_gp = gestionnaire.getCurrentUser().getId();

            while (rs.next()) {
                id_gp = rs.getInt(1);
                txt_file_name.setText(Integer.toString(id_gp));
                PreparedStatement stat2 = conn.prepareStatement(
                        "(SELECT id_bookmark FROM bookmark_group WHERE id_group = '" + id_gp + "')");

                ResultSet rs2 = stat2.executeQuery();
                int id_bm = 0;
                id_bm = rs.getInt(1);
                txt_adress.setText(Integer.toString(id_bm));

                String query3 = "SELECT nom_site, Description, Url "
                        + "FROM bookmark "
                        + "WHERE id = ? ";

                PreparedStatement ps3 = conn.prepareStatement(query3);
                ps3.setInt(1, id_bm);

                ResultSet rs3 = ps3.executeQuery();
                String name = null;
                if (rs3.next()) {
                    txt_file_name.setText(rs3.getString(1));
                    txt_adress.setText(rs3.getString(3));

                }

                items.add(rs3.getString(1));
                list_mp.setItems(items);

            }

        } finally {
            conn.close();

        }

    }

    @FXML
    private void refreshPage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        getBookMark();
    }

    @FXML
    private void info_selected(MouseEvent event) {
    }

    @FXML
    private void showHelp(MouseEvent event) {
    }

    @FXML
    private void removeUserFromBm(MouseEvent event) {
    }

    @FXML
    private void addUserBm(MouseEvent event) throws IOException {
        
              Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajoutPartage_v1.fxml"));

        Connexion_v1Controller controll = new Connexion_v1Controller();
        Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
        controll.setPrevStage(stageTheLabelBelongs);

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void remove_bm(MouseEvent event) {
    }

    @FXML
    private void add_file(MouseEvent event) {
    }

    @FXML
    private void Add_Bm(MouseEvent event) throws SQLException {
        
        
        Bookmark bm = new Bookmark(txt_file_name.getText(),txt_adress.getText(),txt_tag_name.getText());
        
        GestionnaireBM gestionBm = new GestionnaireBM();
        gestionBm.setBookMark(bm);
        gestionBm.addBm();
      
    }

    @FXML
    private void show_group(MouseEvent event) throws IOException {
        
              Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/InfoPartage_v1.fxml"));

        Connexion_v1Controller controll = new Connexion_v1Controller();
        Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
        controll.setPrevStage(stageTheLabelBelongs);

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void remove_File(MouseEvent event) {
        
        
    }

    @FXML
    private void addTag(MouseEvent event)
            throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajout_tag_v1.fxml"));

        Connexion_v1Controller controll = new Connexion_v1Controller();
        Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
        controll.setPrevStage(stageTheLabelBelongs);

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();
    }

}
