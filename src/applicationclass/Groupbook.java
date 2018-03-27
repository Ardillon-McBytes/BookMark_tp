/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe pour les dossiers de marquepage
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Groupbook implements DBField {

  // P-e que ce serait mieux de faire la liste des bookmarks et des groupbooks 
  //    uniquement à partir du main (parce que sinon un bookmark unique peut être 
  //    dans plusieurs groupbook et il faudrait de toute façon scanner tous 
  //    les groupbooks qu'on veux pour le savoir... même chose pour les groupbooks?) 
  private int id;
  private String nom;
  private String description;
  private int groupbookRacine;
  private int groupbookParent;
  private ArrayList<Integer> partages;
  private ArrayList<Integer> groupbookContenus;
  private ArrayList<Integer> bookmarks;

  
  /**
   * 
   */
  public Groupbook() {}
  
  /**
   * 
   */
  public Groupbook(int id, String nom, String description, int groupbookParent) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.groupbookParent = groupbookParent;
  }
  
  /**
   *
   * @param id
   * @param nom
   * @param description
   * @param groupbookParent
   * @param groupbookContenus
   * @param bookmarks
   * @param partages
   */
  public Groupbook(int id, String nom, String description, int groupbookParent,
          ArrayList<Integer> groupbookContenus, ArrayList<Integer> bookmarks,
          ArrayList<Integer> partages) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.groupbookParent = groupbookParent;
    this.groupbookContenus = groupbookContenus;
    this.bookmarks = bookmarks;
    this.partages = partages;
  }

  /**
   *
   * @param id
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @param nom
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   *
   * @param value
   */
  @Override
  public void setValue(String value) {
    setDescription(value);
  }

  /**
   *
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   *
   * @param groupbookParent
   * @throws Exception
   */
  public void setParent(Groupbook groupbookParent) throws Exception {
    if (groupbookParent == null) {
      throw new Exception("Groupbook non initialisé");
    }
    this.groupbookParent = groupbookParent.getId();
  }
  
  /**
   *
   * @param groupbookParent
   */
  public void setParent(int groupbookParent) {
    this.groupbookParent = groupbookParent;
  }
  
  /**
   *
   * @param groupBooks
   */
  public void setGroupBooks(ArrayList<Integer> groupBooks) {
    this.groupbookContenus = groupBooks;
  }
  
  /**
   *
   * @param bookmark
   */
  public void setBookmarks(ArrayList<Integer> bookmark) {
    this.bookmarks = bookmark;
  }
  
  /**
   *
   * @param partages
   */
  public void setPartages(ArrayList<Integer> partages) {
    this.partages = partages;
  }
  
  /**
   *
   * @return
   */
  @Override
  public int getId() {
    return id;
  }
  
  /**
   *
   * @return
   */
  @Override
  public String getNom() {
    return nom;
  }
  
  /**
   *
   * @return
   */
  @Override
  public String getValue() {
    return getDescription();
  }
  
  /**
   *
   * @return
   */
  public String getDescription() {
    return description;
  }
  
  /**
   *
   * @return
   */
  public int getParent() {
    return groupbookParent;
  }
  
  /**
   *
   * @param folderList
   * @param groupbooks
   * @return
   */
  public Groupbook getParent(TA_GB_GB folderList, ArrayList<Groupbook> groupbooks) {
    ArrayList<Groupbook> gbs = Recherche.getParentGroupbooks(this, folderList, groupbooks);
    for (Groupbook gb : gbs) {
      if (groupbookParent == gb.getId()) {
        return gb;
      }
    }
    return null;
  }
  
  /**
   *
   * @return
   */
  public ArrayList<Integer> getGroupbooks() {
    return groupbookContenus;
  }
  
  /**
   *
   * @return
   */
  public ArrayList<Integer> getBookmarks() {
    return bookmarks;
  }

  /**
   *
   * @return
   */
  public ArrayList<Integer> getPartages() {
    return partages;
  }

  /**
   *
   * @param partages
   * @param users
   * @return
   */
  public ArrayList<User> getPartages(TA_User_GB partages, ArrayList<User> users) {
    return Recherche.getLefts(this, partages, users);
  }

  /**
   *
   * @return
   */
  public int getTypePartage() throws Exception {
    G_GB.getAllGroupbooks();
    return 0;
  }

  /**
   *
   * @return
   */
  public boolean isPartager() {
    return !partages.isEmpty();
  }

  /**
   *
   * @param gb
   * @return
   */
  /*public boolean addChild(Integer gb) throws IOException {
    if (gb == null || gb < 1) {
      throw new IOException("Vale");
    }
    for (Integer i : groupbookContenus) {
      if (gb.equals(i)) {
        
      }
    }
    if (gb < 1 || true) { //G_GB.existe(gb)
      return false;
    }
    
    
    
    if (!groupbookContenus.stream().noneMatch((b) -> (b.getId() == gb.getId()))) {
      return false;
    }
    groupbookContenus.add(gb);
    return true;
  }*/
  /**
   *
   * @param bm
   * @return
   */
  public boolean addBookmark(Integer bm) {
    if (bm < 1 || true/*G_BM.existe(bm)*/) {
      return false;
    }
    //G_BM.add(bm);
    bookmarks.add(bm);
    return true;
  }

}
