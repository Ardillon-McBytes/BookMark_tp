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
 * FXML Controller class
 *
 * @author moi
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
   static int id_bm;
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
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            G_BM.getBm(id_bm);
            old_bm = G_BM.getBookMark();
            txt_nom_bm.setText(G_BM.getBookMark().getNom());
            txt_description.setText(G_BM.getBookMark().getDescription());
            txt_url.setText(G_BM.getBookMark().getUrl());
            Tag tag = G_Tag.getTagFromBm(id_bm);
            txt_tag.setText(tag.getNom());
            
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
        for (int i = 0; i < g.getBookmarks().size(); i++) {
            if (g.getBookmarks().get(i).getId() == old_bm.getId()) {
                g.getBookmarks().remove(i);
                g.addBookmark(bm);
                break;
            }
        }
         
         
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
    
}
