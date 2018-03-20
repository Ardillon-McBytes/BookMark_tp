/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe qui opère les changements des données de l'applications
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Gestionnaire {
  /**
   * ATTRIBUTS DE LA CLASSE
   ************************/
  private User current;   // Compte actif?
  private TA_User_GB acces;
  private TA_GB_GB conteneurs;
  private TA_GB_BM contenus;
  private TA_BM_Tag etiquettes;
  private ArrayList<User> users;
  private ArrayList<Groupbook> groupbooks;
  private ArrayList<Bookmark> bookmarks;
  private ArrayList<Tag> tags;
  
  private ArrayList<Integer> identifiants; // Utile?
  
  /**
   * Constructeur de la classe
   */
  public Gestionnaire() {
    acces = new TA_User_GB();
    conteneurs = new TA_GB_GB();
    contenus = new TA_GB_BM();
    etiquettes = new TA_BM_Tag();
    users = new ArrayList<>();
    groupbooks = new ArrayList<>();
    bookmarks = new ArrayList<>();
    tags = new ArrayList<>();
    identifiants = new ArrayList<>();     
  }
  
  /**
   * MÉTHODES DE LA CLASSE
   ***********************/
  
  /**
   * 
   * @param u 
   */
  public void addUser(User u) {
    users.add(u);
  }
  
  /**
   * 
   * @param gb 
   */
  public void addGroupbook(Groupbook gb) {
    groupbooks.add(gb);
  }
  
  /**
   * 
   * @param bm 
   */
  public void addBookmark(Bookmark bm) {
    bookmarks.add(bm);
  }
  
  /**
   * 
   * @param t
   */
  public void addTag(Tag t) {
    tags.add(t);
  }
  
  /**
   * 
   */
  public void addId(int i) {
    identifiants.add(i);
  }
  
  /**
   * 
   */
  public void add() {
    //.add();
  }
  
  
  /**
   * GETTEURS ET SETTEURS DE BASE
   ******************************/
  
  /**
   * Obtien la table d'association (TA) qui lie les utilisateurs à leurs 
   * @return 
   */
  public TA_User_GB getAcces() {
    return acces;
  }

  public TA_GB_GB getConteneurs() {
    return conteneurs;
  }

  public TA_GB_BM getContenus() {
    return contenus;
  }

  public TA_BM_Tag getEtiquettes() {
    return etiquettes;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public ArrayList<Groupbook> getGroupbooks() {
    return groupbooks;
  }

  public ArrayList<Bookmark> getBookmarks() {
    return bookmarks;
  }

  public ArrayList<Tag> getTags() {
    return tags;
  }

  public ArrayList<Integer> getIdentifiants() {
    return identifiants;
  }

  public void setAcces(TA_User_GB acces) {
    this.acces = acces;
  }

  public void setConteneurs(TA_GB_GB conteneurs) {
    this.conteneurs = conteneurs;
  }

  public void setContenus(TA_GB_BM contenus) {
    this.contenus = contenus;
  }

  public void setEtiquettes(TA_BM_Tag etiquettes) {
    this.etiquettes = etiquettes;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public void setGroupbooks(ArrayList<Groupbook> groupbooks) {
    this.groupbooks = groupbooks;
  }

  public void setBookmarks(ArrayList<Bookmark> bookmarks) {
    this.bookmarks = bookmarks;
  }

  public void setTags(ArrayList<Tag> tags) {
    this.tags = tags;
  }

  public void setIdentifiants(ArrayList<Integer> identifiants) {
    this.identifiants = identifiants;
  }
}
