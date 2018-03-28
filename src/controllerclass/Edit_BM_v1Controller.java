/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package controllerclass;

import applicationclass.Bookmark;
import applicationclass.G_BM;
import applicationclass.G_GB;
import applicationclass.G_Tag;
import applicationclass.TA_BM_Tag;
import applicationclass.TA_GB_BM;
import applicationclass.Tag;
import static controllerclass.main_controller.g;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Classe du Controlleur pour l'édition des bookmarks
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Edit_BM_v1Controller implements Initializable {

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
  int id_bm;
  @FXML
  private TextField txt_nom_bm;
  @FXML
  private TextField txt_description;
  @FXML
  private TextField txt_url;
  @FXML
  private TextField txt_tag;

  Bookmark old_bm = new Bookmark();

  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      // TODO
      //g.getBm(id_bm);
      old_bm = G_BM.getBookMark();
      txt_nom_bm.setText(G_BM.getBookMark().getNom());
      txt_description.setText(G_BM.getBookMark().getDescription());
      txt_url.setText(G_BM.getBookMark().getUrl());
      Tag tag1 = G_Tag.getTagFromBm(id_bm);
      txt_tag.setText(tag1.getNom());

    } catch (SQLException ex) {
      Logger.getLogger(Edit_BM_v1Controller.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public void setPrevStage(Stage stage, int id_bm) {
    prevStage = stage;
    this.id_bm = id_bm;
  }

  /**
   * retourne l'id du tag selon son nom
   */
  /**
   * ajoute un tag a la liste
   */
  private void editTag(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {

  }

  /**
   * ajoute un tag a un bookmark
   */
  @FXML
  private void exitPage(MouseEvent event) {
  }

  @FXML
  private void editBookmark(MouseEvent event) throws SQLException {
    Bookmark bm = new Bookmark();
    bm.setNom(txt_nom_bm.getText());
    bm.setDescription(txt_description.getText());
    bm.setUrl(txt_url.getText());
    bm.setId(old_bm.getId());

    G_BM.setBookMark(bm);
    G_BM.editBm(old_bm.getId(), bm);

    Tag tag1 = G_Tag.getTagFromName(txt_tag.getText());
    if (tag1.getId() < 1) {
      G_Tag.setTag(txt_tag.getText(), "");
      G_Tag.addTag();

    }
    tag1 = G_Tag.getTagFromName(txt_tag.getText());
    TA_BM_Tag.addTagToBm(G_BM.getBookMark().getId(), tag1.getId());

    Stage stageTheLabelBelongs = (Stage) btnAnnuler.getScene().getWindow();
    stageTheLabelBelongs.hide();
  }

}
