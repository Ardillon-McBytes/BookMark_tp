/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe pour les dossiers de marquepage
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
  private int groupbookParent;
  private ArrayList<Groupbook> groupbookContenus;
  private ArrayList<Bookmark> bookmarks;
  private ArrayList<Integer> partages;
  private int typePartage;
  
  /**
   *
   * @param id
   * @param nom
   * @param description
   * @param groupbookParent
   * @param groupbookContenus
   * @param bookmarks
   * @param partages
   * @param typePartage
   */
  public Groupbook(int id, String nom, String description, int groupbookParent,
          ArrayList<Groupbook> groupbookContenus, ArrayList<Bookmark> bookmarks, 
          ArrayList<Integer> partages, int typePartage) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.groupbookParent = groupbookParent;
    this.groupbookContenus = groupbookContenus;
    this.bookmarks = bookmarks;
    this.partages = partages;
    this.typePartage = typePartage;
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
    if (groupbookParent == null) throw new Exception("Groupbook non initialisé");
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
  public void setGroupBooks(ArrayList<Groupbook> groupBooks) {
    this.groupbookContenus = groupBooks;
  }
  
  /**
   *
   * @param bookmark
   */
  public void setBookmarks(ArrayList<Bookmark> bookmark) {
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
   * @param typePartage
   */
  public void setTypePartage(int typePartage) {
    this.typePartage = typePartage;
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
  public ArrayList<Groupbook> getGroupBooks() {
    return groupbookContenus;
  }

  /**
   *
   * @return
   */
  public ArrayList<Bookmark> getBookmarks() {
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
    return Recherche.getUsers(this, partages, users);
  }

  /**
   *
   * @return
   */
  public int getTypePartage() {
    return typePartage;
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
   * @param bm
   * @param tas
   * @return
   */
  public boolean add(Bookmark bm, TA_GB_BM tas) {
    // Utiliser la méthode de la classe Recherche au lieu?
    if (!bookmarks.stream().noneMatch((b) -> (b.getId() == bm.getId()))) {
      return false;
    }
    bookmarks.add(bm);
    return true;
  }
  
  /**
   *
   * @param gb
   * @return
   */
  public boolean add(Groupbook gb) {
    if (!groupbookContenus.stream().noneMatch((b) -> (b.getId() == gb.getId()))) {
      return false;
    }
    groupbookContenus.add(gb);
    return true;
  }

  
}
