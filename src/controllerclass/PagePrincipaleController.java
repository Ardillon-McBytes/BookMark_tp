/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.Bookmark;
import applicationclass.Gestionnaire;
import static applicationclass.Gestionnaire.*;
import applicationclass.G_BM;
import applicationclass.G_GB;
import applicationclass.G_Tag;
import applicationclass.G_User;
import applicationclass.Groupbook;
import applicationclass.TA_BM_Tag;
import applicationclass.TA_GB_BM;
import applicationclass.TA_User_GB;
import applicationclass.Tag;
import applicationclass.User;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

/**
 * FXML Controller class
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class PagePrincipaleController extends main_controller implements Initializable {

    Stage stageTheLabelBelongs;
  
    @FXML
    private GridPane gp_principal;
    @FXML
    private TextField txt_file_name;
    @FXML
    private TextField txt_adress;
    @FXML
    private ImageView btnInfo_tag;
    @FXML
    private ImageView btnAdd_txt_tag;

    @FXML
    private ListView<String> list_mp = new ListView<String>();
    ;

    static Stage prevStage;
    static String _userName;
    static int _user_id;
    @FXML
    private TextField txt_tag_name;
    @FXML
    private ImageView imgRefresh;
    @FXML
    private ListView<String> list_tag = new ListView<String>();
    @FXML
    private ListView<String> list_user = new ListView<String>();
    private ImageView btn_share;
    @FXML
    private ImageView btn_add_gb;
    @FXML
    private ImageView btn_remove_gb;
    @FXML
    private ImageView btn_show_group;
    @FXML
    private ImageView btn_remove_bm;
    @FXML
    private ImageView btn_add_bm;
    @FXML
    private ImageView btn_show_tag;
    @FXML
    private ImageView btn_help;

    int currentBm_id = 0;
    int id_selected_user = 0;
    int id_selection_gb = 0;
    @FXML
    private ImageView btn_remove_share;
  @FXML
  private ImageView btn_disconnect;
  @FXML
  private ImageView btn_info;
  @FXML
  private ImageView btnVerifier_url;
    @FXML
    private ListView<String> list_Gb = new ListView<String>();
    @FXML
    private ImageView btn_show_tag2;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initListeTag();
        initListeBm();
        initBm();
        initListePartage();
        try {
            initListGb();
        } catch (SQLException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  /**
   *
   */
  public void initBm() {
        try {
            getBookMark();
        } catch (SQLException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  /**
   *
   */
  public void initListeTag() {
        list_tag.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                TA_BM_Tag ta = new TA_BM_Tag();
                try {
                    G_Tag.getTagFromName(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    loadOneTag(TA_BM_Tag.getBmFromTag(G_Tag.getTag().getId()), G_Tag.getTag().getNom());
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

  /**
   *
   * @throws SQLException
   * @throws IOException
   */
  public void initListGb() throws SQLException, IOException {

        list_Gb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<Bookmark> list_bm = new ArrayList<Bookmark>();
                try {
                    list_bm = TA_GB_BM.getBmFromGb(G_GB.getGBId(newValue));
                    loadUserGroup(list_bm);
                    id_selection_gb = G_GB.getGBId(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        loadUserGroup();
    }

  /**
   *
   */
  public void initListeBm() {
        list_mp.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try {
                    G_BM.getBm(newValue);
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                txt_file_name.setText(G_BM.getBookMark().getNom());
                txt_adress.setText(G_BM.getBookMark().getUrl());
                try {
                    String tagName = G_Tag.getTagFromBm(G_BM.getBookMark().getId()).getNom();
                    txt_tag_name.setText(tagName);
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    currentBm_id = G_BM.getBm(newValue).getId();

                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                btn_share.setDisable(false);
                btn_remove_bm.setDisable(false);
            }
        });

    }

  /**
   *
   */
  public void initListePartage() {
        list_user.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

              
                try {
                    G_User.getUserId(newValue).getId();
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }

                ArrayList<Groupbook> gb = new ArrayList<Groupbook>();
                try {
                    gb = G_GB.getGBFromUser(G_User.getUserId(newValue).getId());                   
                    
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
             

                try {
                    loadPartage(gb);
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    id_selected_user = G_User.getUserId(newValue).getId();
                } catch (SQLException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

  /**
   *
   */
  public void showBmList() {

    }

    void loadUserShare() throws SQLException, IOException {
        ArrayList<User> users = new ArrayList<User>();

        users = G_GB.getUserFromGB(g.getGroupbooks());

        ObservableList items = list_user.getItems();
        for (int i = 0; i < users.size(); i++) {
            if (!items.contains(users.get(i).getNom())) {
                items.add(users.get(i).getNom());
            }

        }
        list_user.setItems(items);
        list_user.refresh();

    }

    void loadUserGroup() throws SQLException, IOException {
        ArrayList<Groupbook> list_gb = new ArrayList<Groupbook>();
        list_gb = G_GB.getGBFromUser(g.getUsagerActif().getId());
        ObservableList items = list_Gb.getItems();
        for (int i = 0; i < list_gb.size(); i++) {
            items.add(list_gb.get(i).getNom());
        }
        list_Gb.setItems(items);
        list_Gb.refresh();
    }

    void loadUserGroup(ArrayList<Bookmark> bm) throws SQLException, IOException {

        
         gp_principal.getChildren().clear();
        ObservableList items = FXCollections.observableArrayList();
        Label txtnom = new Label();;
        Label txtTag = new Label();;
        Label txtUrl = new Label();;
        for (int i = 0; i < bm.size(); i++) {

            {
                
                txtnom = new Label();
                txtUrl = new Label();
                Tag tag = new Tag();
               
                tag =  G_Tag.getTagFromBm(bm.get(i).getId());
                String testTag = tag.getNom();
                if (!items.contains(G_Tag.getTag().getNom())) {
                    items.add(bm.get(i).getNom());
                    txtnom.setText(bm.get(i).getNom());
                    txtnom.setPrefWidth(800);
                    txtUrl.setText(bm.get(i).getUrl());
                   
                
                    gp_principal.add(txtnom, 0, i);
                    gp_principal.add(txtTag, 1, i);
                    gp_principal.add(txtUrl, 2, i);
                }
            }

        }
    }

    void getTA_BM(int idTag) throws SQLException {

        for (int i = 0; i < g.getBookmarks().size(); i++) {

            ObservableList items = FXCollections.observableArrayList(TA_BM_Tag.getBmFromTag(g.getBookmarks().get(i).getId()));
        }

    }

    void loadPartage(ArrayList<Groupbook> gb) throws SQLException {
        gp_principal.getChildren().clear();
        ObservableList items = FXCollections.observableArrayList();

        Label txtnom = new Label();
        Label txtTag = new Label();
        Label txtUrl = new Label();
        int n = 0;
        for (int j = 0; j < gb.size(); j++) {

            for (int i = 0; i < gb.get(j).getBookmarks().size(); i++) {
                G_Tag.getTagFromBm(gb.get(j).getBookmarks().get(i).getId());
                txtnom = new Label();
                txtUrl = new Label();
                String testTag = G_Tag.getTagFromBm(gb.get(j).getBookmarks().get(i).getId()).getNom();
                if (!items.contains(G_Tag.getTag().getNom())) {
                    items.add(gb.get(j).getBookmarks().get(i).getNom());
                    txtnom.setText(gb.get(j).getBookmarks().get(i).getNom());
                    txtnom.setPrefWidth(800);
                    txtUrl.setText(gb.get(j).getBookmarks().get(i).getUrl());
                    txtTag = new Label(G_Tag.getTagFromBm(gb.get(j).getBookmarks().get(i).getId()).getNom());
                    gp_principal.add(txtnom, 0, n);
                    gp_principal.add(txtTag, 1, n);
                    gp_principal.add(txtUrl, 2, n);
                    n++;
                }
            }
        }
    }

    void loadOneTag(ArrayList<Bookmark> bookmarks, String tagName) throws SQLException {
        gp_principal.getChildren().clear();
        ObservableList items = FXCollections.observableArrayList();

        Label txtnom;
        Label txtTag;
        Label txtUrl;
        for (int j = 0; j < g.getGroupbooks().size(); j++) {
            for (int i = 0; i < g.getGroupbooks().get(j).getBookmarks().size(); i++) {
                G_Tag.getTagFromBm(g.getGroupbooks().get(j).getBookmarks().get(i).getId());
                txtnom = new Label();
                txtUrl = new Label();
                String testTag = G_Tag.getTagFromBm(g.getGroupbooks().get(j).getBookmarks().get(i).getId()).getNom();
                if (!items.contains(G_Tag.getTag().getNom()) && testTag.equals(tagName)) {
                    items.add(g.getGroupbooks().get(j).getBookmarks().get(i).getNom());
                    txtnom.setText(g.getGroupbooks().get(j).getBookmarks().get(i).getNom());
                    txtnom.setPrefWidth(800);
                    txtUrl.setText(g.getGroupbooks().get(j).getBookmarks().get(i).getUrl());
                    txtTag = new Label(G_Tag.getTagFromBm(g.getGroupbooks().get(j).getBookmarks().get(i).getId()).getNom());
                    gp_principal.add(txtnom, 0, i);
                    gp_principal.add(txtTag, 1, i);
                    gp_principal.add(txtUrl, 2, i);
                }
            }
        }
    }

    void loadTag(ArrayList<Bookmark> bookmarks) throws SQLException {
        ObservableList items = list_tag.getItems();

        for (int j = 0; j < g.getGroupbooks().size(); j++) {
            for (int i = 0; i < g.getGroupbooks().get(j).getBookmarks().size(); i++) {
                G_Tag.getTagFromBm(g.getGroupbooks().get(j).getBookmarks().get(i).getId());

                if (!items.contains(G_Tag.getTag().getNom())) {
                    items.add(G_Tag.getTag().getNom());
                }
            }
            list_tag.setItems(items);
           list_tag.refresh();
        }

    }

    void getBookMark() throws IOException, SQLException, ClassNotFoundException, Exception {

        Connection conn = SimpleDataSource.getConnection();
        ObservableList items = list_mp.getItems();
        try {
            User user = new User();
            user = g.getUsagerActif();
            g.loadUserGb();
            loadTag(g.getBookmarks());
            loadUserGroup();
            Label txtnom;
            Label txtTag;
            Label txtUrl;
            int nb = 0;
            for (int j = 0; j < g.getGroupbooks().size(); j++) {

                for (int i = 0; i < g.getGroupbooks().get(j).getBookmarks().size(); i++) {

                    txtnom = new Label();

                    txtUrl = new Label();
                    if (!items.contains(g.getGroupbooks().get(j).getBookmarks().get(i).getNom())) {
                        items.add(g.getGroupbooks().get(j).getBookmarks().get(i).getNom());
                        txtnom.setText(g.getGroupbooks().get(j).getBookmarks().get(i).getNom());
                        txtnom.setPrefWidth(800);
                        txtUrl.setText(g.getGroupbooks().get(j).getBookmarks().get(i).getUrl());
                        txtTag = new Label(G_Tag.getTagFromBm(g.getGroupbooks().get(j).getBookmarks().get(i).getId()).getNom());
                        gp_principal.add(txtnom, 0, nb);
                        gp_principal.add(txtTag, 1, nb);
                        gp_principal.add(txtUrl, 2, nb);
                        nb++;
                    }
                }
            }
            list_mp.setItems(items);
list_mp.refresh();
        } finally {
            conn.close();

        }

    }

    private void refreshPage(MouseEvent event) throws IOException, SQLException, ClassNotFoundException, Exception {
        initListeTag();
        initListeBm();
        initBm();
        initListePartage();
        try {
            initListGb();
        } catch (SQLException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    @FXML
    private void showTag(MouseEvent event) throws IOException {

    }

    @FXML
    private void showMpInfo(MouseEvent event) {

    }

    @FXML
    private void showBmGroup(MouseEvent event) {
      
    }

    private void add_share(MouseEvent event) throws IOException {

        if (currentBm_id > 0) {

            Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajoutPartage_v1.fxml"));

            AjoutPartage_v1Controller controll = new AjoutPartage_v1Controller();
            Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
            controll.setPrevStage(stageTheLabelBelongs, 1);

            Scene scene = new Scene(root);
            Stage secondStage = new Stage();

            secondStage.setScene(scene);
            secondStage.showAndWait();
            initListeTag();
            initListeBm();
            initBm();
            initListePartage();
        }
    }

    @FXML
    private void add_gb(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        
      
         Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajout_gb_v1.fxml"));

            AjoutPartage_v1Controller controll = new AjoutPartage_v1Controller();
            Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
            controll.setPrevStage(stageTheLabelBelongs, 1);

            Scene scene = new Scene(root);
            Stage secondStage = new Stage();

            secondStage.setScene(scene);
            secondStage.showAndWait();
    }


    @FXML
    private void remove_gb(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        
        TA_User_GB.deleteUserGroup(id_selected_user, id_selection_gb);
       list_user.getItems().remove(G_User.getUserName(id_selected_user));
      list_user.refresh();
                
    }

    @FXML
    private void add_bm(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajout_bm_v1.fxml"));

        AjoutBm_v1Controller controll = new AjoutBm_v1Controller();
        Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
        controll.setPrevStage(stageTheLabelBelongs, currentBm_id);

        Scene scene = new Scene(root);
        Stage secondStage = new Stage();

        secondStage.setScene(scene);
        secondStage.show();
    }

    @FXML
    private void show_help(MouseEvent event) {
    }

    @FXML
    private void show_info(MouseEvent event) {
    }

    @FXML
    private void remove_bm(MouseEvent event)
            throws IOException, SQLException, ClassNotFoundException {

        TA_GB_BM.setId(currentBm_id);
        TA_GB_BM.delete_GB_BM();
        G_BM.getBookMark().setId(currentBm_id);
        G_BM.deleteBm();
        Bookmark bm = new Bookmark();
        for (int j = 0; j < g.getGroupbooks().size(); j++) {

            for (int i = 0; i < g.getGroupbooks().get(j).getBookmarks().size(); i++) {
                if (g.getGroupbooks().get(j).getBookmarks().get(i).getId() == currentBm_id) {
                    bm = g.getGroupbooks().get(j).getBookmarks().get(i);
                    g.getGroupbooks().get(j).getBookmarks().remove(bm);
                    list_mp.getItems().remove(bm.getNom());
                    list_mp.refresh();
                    break;
                }
            }
        }
    }

    @FXML
    private void remove_share_user(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {

        TA_GB_BM.getIdGb(currentBm_id);
        TA_User_GB.deleteUserGroup(id_selected_user, TA_GB_BM.getIdGb());

        list_user.getItems().remove(G_User.getUserName(id_selected_user));
        list_user.refresh();
    }

    @FXML
    private void add_tag(MouseEvent event)
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

  @FXML
  private void disconnect(MouseEvent event) {
    try {
      g.setUsagerActif(new User("déconnecté"));
      
      Connexion_v1Controller controller = new Connexion_v1Controller();
      stageTheLabelBelongs = (Stage) btn_disconnect.getScene().getWindow();
      controller.setPrevStage(stageTheLabelBelongs);

      Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/connexion_v1.fxml"));

      Scene scene = new Scene(root);
      Stage secondStage = new Stage();

      secondStage.setScene(scene);

      stageTheLabelBelongs.hide();
      secondStage.show();

    } catch (IOException e) {
      g.addMessageErreur("Mauvais format pour le nom de l'utilisateur");
    } catch (SQLException ex) {
      g.addMessageErreur("Erreur avec la BD");
    }
  }

  @FXML
  private void refresh(MouseEvent event) {
  }

  @FXML
  private void verifier_url(MouseEvent event) {
  }

    @FXML
    private void showGroupBm(MouseEvent event) {
    }
}
