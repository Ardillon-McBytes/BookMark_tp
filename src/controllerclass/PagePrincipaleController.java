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
import javafx.scene.input.KeyEvent;
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
  private ImageView btn_disconnect;
  @FXML
  private ImageView btn_info;
  @FXML
  private ImageView btnVerifier_url;
  @FXML
  private ListView<String> list_Gb = new ListView<String>();
  @FXML
  private ImageView btn_show_tag2;
  @FXML
  private ImageView btn_add_share;
  @FXML
  private ImageView imgRefresh1;

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
    try {
      initListePartage();
    } catch (SQLException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      initListGb();
    } catch (SQLException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void initBm() {
    try {
      loadBookmark();
    } catch (SQLException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

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
          loadOneTag(TA_BM_Tag.getBmFromTag(G_Tag.getTag().getId()), newValue);
        } catch (SQLException ex) {
          Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        }

      }
    });

  }

  public void initListGb() throws SQLException, IOException {

    list_Gb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override

      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Groupbook list_bm = new Groupbook();
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
//                btn_share.setDisable(false);
//                btn_remove_bm.setDisable(false);
      }
    });

  }

  public void initListePartage() throws SQLException, IOException {
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
          id_selected_user = G_User.getUserId(newValue).getId();
          User u = new User();
          u.setId(id_selected_user);
          u.setNom(newValue);
          ArrayList<Groupbook> gp = TA_User_GB.getUserGroups(id_selected_user);

          loadSharePartage(gp.get(0));

        } catch (SQLException ex) {
          Logger.getLogger(PagePrincipaleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
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
    loadUserShare();

  }

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
      if (!items.contains(list_gb.get(i).getNom())) {
        items.add(list_gb.get(i).getNom());
      }

    }
    list_Gb.setItems(items);
    list_Gb.refresh();
  }

  void loadUserGroup(Groupbook gb) throws SQLException, IOException {

    gp_principal.getChildren().clear();
    ObservableList items = FXCollections.observableArrayList();
    Label txtnom = new Label();
    Label txtTag = new Label();
    Label txtUrl = new Label();
    int n = 0;
    for (int i = 0; i < gb.getBookmarks().size(); i++) {

      {

        txtnom = new Label();
        txtUrl = new Label();
        Tag tag = new Tag();

        tag = G_Tag.getTagFromBm(gb.getBookmarks().get(i).getId());
        String testTag = tag.getNom();
        if (!items.contains(G_Tag.getTag().getNom())) {
          items.add(gb.getBookmarks().get(i).getNom());
          txtnom.setText(gb.getBookmarks().get(i).getNom());
          txtnom.setPrefWidth(800);
          txtUrl.setText(gb.getBookmarks().get(i).getUrl());
          txtTag = new Label(G_Tag.getTagFromBm(gb.getBookmarks().get(i).getId()).getNom());
          gp_principal.add(txtnom, 0, n);
          gp_principal.add(txtTag, 1, n);
          gp_principal.add(txtUrl, 2, n);
          n++;
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

  void loadSharePartage(Groupbook gb) throws SQLException {
    gp_principal.getChildren().clear();
    ObservableList items = FXCollections.observableArrayList();

    Label txtnom = new Label();
    Label txtTag = new Label();
    Label txtUrl = new Label();
    int n = 0;

    for (int i = 0; i < gb.getBookmarks().size(); i++) {
      G_Tag.getTagFromBm(gb.getBookmarks().get(i).getId());
      txtnom = new Label();
      txtUrl = new Label();
      String testTag = G_Tag.getTagFromBm(gb.getBookmarks().get(i).getId()).getNom();
      if (!items.contains(G_Tag.getTag().getNom())) {
        items.add(gb.getBookmarks().get(i).getNom());
        txtnom.setText(gb.getBookmarks().get(i).getNom());
        txtnom.setPrefWidth(800);
        txtUrl.setText(gb.getBookmarks().get(i).getUrl());
        txtTag = new Label(G_Tag.getTagFromBm(gb.getBookmarks().get(i).getId()).getNom());
        gp_principal.add(txtnom, 0, n);
        gp_principal.add(txtTag, 1, n);
        gp_principal.add(txtUrl, 2, n);
        n++;
      }
    }

  }

  void loadOneTag(ArrayList<Bookmark> bookmarks, String tagName) throws SQLException {
    gp_principal.getChildren().clear();
    ObservableList items = FXCollections.observableArrayList();

    Label txtnom;
    Label txtTag;
    Label txtUrl;
    int n = 0;
    for (int i = 0; i < g.getBookmarks().size(); i++) {
      G_Tag.getTagFromBm(g.getBookmarks().get(i).getId());
      txtnom = new Label();
      txtUrl = new Label();
      String testTag = G_Tag.getTagFromBm(g.getBookmarks().get(i).getId()).getNom();
      if (!items.contains(G_Tag.getTag().getNom()) && testTag.equals(tagName)) {
        items.add(g.getBookmarks().get(i).getNom());
        txtnom.setText(g.getBookmarks().get(i).getNom());
        txtnom.setPrefWidth(800);
        txtUrl.setText(g.getBookmarks().get(i).getUrl());
        txtTag = new Label(G_Tag.getTagFromBm(g.getBookmarks().get(i).getId()).getNom());
        gp_principal.add(txtnom, 0, n);
        gp_principal.add(txtTag, 1, n);
        gp_principal.add(txtUrl, 2, n);
        n++;
      }
    }

  }

  void loadTag(ArrayList<Bookmark> bookmarks) throws SQLException {
    ObservableList items = list_tag.getItems();

    for (int i = 0; i < g.getBookmarks().size(); i++) {
      G_Tag.getTagFromBm(g.getBookmarks().get(i).getId());

      if (!items.contains(G_Tag.getTag().getNom())) {
        items.add(G_Tag.getTag().getNom());
      }
    }
    list_tag.setItems(items);
    list_tag.refresh();
  }

  void loadBookmark() throws IOException, SQLException, ClassNotFoundException, Exception {

    Connection conn = SimpleDataSource.getConnection();
    ObservableList items = FXCollections.observableArrayList();
    list_mp.getItems().clear();
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

      for (int i = 0; i < g.getBookmarks().size(); i++) {

        txtnom = new Label();

        txtUrl = new Label();
        if (!items.contains(g.getBookmarks().get(i).getNom())) {
          items.add(g.getBookmarks().get(i).getNom());
          txtnom.setText(g.getBookmarks().get(i).getNom());
          txtnom.setPrefWidth(800);
          txtUrl.setText(g.getBookmarks().get(i).getUrl());
          txtTag = new Label(G_Tag.getTagFromBm(g.getBookmarks().get(i).getId()).getNom());
          gp_principal.add(txtnom, 0, nb);
          gp_principal.add(txtTag, 1, nb);
          gp_principal.add(txtUrl, 2, nb);
          nb++;
        }

      }
      list_mp.setItems(items);
      list_mp.refresh();
    } finally {
      conn.close();

    }

  }

 

  @FXML
  private void addUserBm(MouseEvent event) throws IOException {

    AjoutPartage_v1Controller controll = new AjoutPartage_v1Controller();
    Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
    controll.setPrevStage(stageTheLabelBelongs, id_selection_gb);
    Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajoutPartage_v1.fxml"));
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

  private void add_share(MouseEvent event) throws IOException, SQLException, Exception {

    if (currentBm_id > 0) {

      Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/ajoutPartage_v1.fxml"));

      AjoutPartage_v1Controller controll = new AjoutPartage_v1Controller();
      Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
      controll.setPrevStage(stageTheLabelBelongs, id_selection_gb);

      Scene scene = new Scene(root);
      Stage secondStage = new Stage();

      secondStage.setScene(scene);
      secondStage.showAndWait();
     refresh();
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

    TA_GB_BM.getIdGb(id_selection_gb);
    TA_User_GB.deleteUserGroup(id_selected_user, id_selection_gb);
    String userNAme = G_User.getUserName(id_selected_user);
    list_user.getItems().remove(userNAme);
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
  private void refresh(MouseEvent event) throws SQLException, IOException, Exception {
   refresh();
  }

    private void refresh() throws SQLException, IOException, Exception {
    loadTag(g.getBookmarks());
    loadPartage(g.getGroupbooks());
    loadUserGroup();
    loadUserShare();
    loadBookmark();
  }
  @FXML
  private void verifier_url(MouseEvent event) {
  }

  @FXML
  private void showGroupBm(MouseEvent event) {
  }

  @FXML
  private void edit_BM(MouseEvent event) throws IOException {

    Edit_BM_v1Controller controll = new Edit_BM_v1Controller();
    Stage stageTheLabelBelongs = (Stage) btnAdd_txt_tag.getScene().getWindow();
    controll.setPrevStage(stageTheLabelBelongs, currentBm_id);
    Parent root = FXMLLoader.load(getClass().getResource("../interfaceclass/edit_bm_v1.fxml"));
    Scene scene = new Scene(root);
    Stage secondStage = new Stage();

    secondStage.setScene(scene);
    secondStage.show();
  }
}
