/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerclass;

import applicationclass.Bookmark;
import applicationclass.G_BM;
import applicationclass.G_GB;
import applicationclass.G_Tag;
import applicationclass.Gestionnaire;
import applicationclass.Groupbook;
import applicationclass.TA_BM_Tag;
import applicationclass.TA_GB_BM;
import applicationclass.Tag;
import static controllerclass.AjoutPartage_v1Controller.prevStage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sqlclass.SimpleDataSource;

/**
 * FXML Controller class
 *
 * @author moi
 */
public class AjoutBm_v1Controller extends main_controller implements Initializable {

  private TextField tag_name;
  @FXML
  private Button btnAnnuler;
  @FXML
  private Button btnAjouter;
  private TextField tag_description;

  static Stage prevStage;
  static int _id_user;
  static int _id_tag = 0;
  static int _id_bookmark;

  public Tag tag;
  int id_gb;
  @FXML
  private TextField txt_nom_bm;
  @FXML
  private TextField txt_description;
  @FXML
  private TextField txt_url;
  @FXML
  private TextField txt_tag;

  /**
   * Initializes the controller class.
   */
  @Override

  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  public void setPrevStage(Stage stage, int id_gb) {
    prevStage = stage;
    this.id_gb = id_gb;
  }

  /**
   * retourne l'id du tag selon son nom
   */
  /**
   * ajoute un tag a la liste
   */
  @FXML
  private void addTag(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

    Bookmark bm = new Bookmark();
    bm.setNom(txt_nom_bm.getText());
    bm.setDescription(txt_description.getText());
    bm.setUrl(txt_url.getText());

    G_BM.setBookMark(bm);
    G_BM.addBm();
    G_BM.getBm(txt_nom_bm.getText());

    int id_Gb = G_GB.getGBDefaultFromUser(g.getUsagerActif().getNom());

    TA_GB_BM.setIdGb(id_Gb);
    TA_GB_BM.setIdBm(G_BM.getBookMark().getId());

    if (TA_GB_BM.getId(TA_GB_BM.getIdGb(), TA_GB_BM.getIdBm()) < 1) {
      TA_GB_BM.add_GB_BM();
    }
    int id_tag;

    Tag tag = G_Tag.getTagFromName(txt_tag.getText());
    if (tag.getId() < 1) {
      G_Tag.setTag(txt_tag.getText(), "");
      G_Tag.addTag();

    }
    tag = G_Tag.getTagFromName(txt_tag.getText());
    TA_BM_Tag.addTagToBm(G_BM.getBookMark().getId(), tag.getId());

    Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
    stageTheLabelBelongs.hide();

  }

  /**
   * ajoute un tag a un bookmark
   */
  @FXML
  private void exitPage(MouseEvent event) {
  }

}
