/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import static applicationclass.G_Validation.*;
import static applicationclass.G_User.*;
import static applicationclass.G_TA.*;
import static applicationclass.G_Partage.*;
import static applicationclass.G_GB.*;
import static applicationclass.G_BM.*;
import static applicationclass.G_Tag.*;
import static applicationclass.Recherche.*;
import static applicationclass.G_Requete.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sqlclass.SimpleDataSource;

/**
 * Classe qui opère les changements des données de l'applications
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Gestionnaire {

  /**
   * ATTRIBUTS DE LA CLASSE *********************
   */
  private static boolean initialise;
  
  private static User usagerActif;   // Compte actif?
  
  private static TA_User_GB acces;
  private static TA_GB_GB conteneurs;
  private static TA_GB_BM contenus;
  private static TA_BM_Tag etiquettes;

  private static ArrayList<User> users;
  private static ArrayList<Groupbook> groupbooks;
  private static ArrayList<Bookmark> bookmarks;
  private static ArrayList<Tag> tags;

  private static ArrayList<Integer> identifiants; // Utile?

  /**
   * Constructeur de la classe
   */
  public Gestionnaire() {
    Gestionnaire.initialise = false;

    //Gestionnaire.gValid = new G_Validation();
    //Gestionnaire.gUser = new G_User();
    Gestionnaire.usagerActif = new User();

    //Gestionnaire.gTA = new G_TA();
    Gestionnaire.acces = new TA_User_GB();
    //Gestionnaire.gPartage = new G_Partage();
    Gestionnaire.conteneurs = new TA_GB_GB();
    //Gestionnaire.gGB = new G_GB();
    Gestionnaire.contenus = new TA_GB_BM();
    //Gestionnaire.gBM = new G_BM();
    Gestionnaire.etiquettes = new TA_BM_Tag();
    //Gestionnaire.gTag = new G_Tag();

    //Gestionnaire.r = new Recherche();
    //Gestionnaire.gRequete = new G_Requete();
    Gestionnaire.users = new ArrayList<>();
    Gestionnaire.groupbooks = new ArrayList<>();
    Gestionnaire.bookmarks = new ArrayList<>();
    Gestionnaire.tags = new ArrayList<>();

    Gestionnaire.identifiants = new ArrayList<>();
  }

  /**
   * Méthode pour faire des tests
   *
   * @param args
   */
  public static void main(String[] args) {

  }

  /**
   * MÉTHODES DE LA CLASSE *********************
   */
  
  /**
   * 
   * 
   * @param nomUtilisateur 
   * @param courriel 
   * @param mdp 
   * @return 
   * @throws IOException 
   * @throws SQLException 
   * @throws Exception 
   */
  public boolean initNouveau(String nomUtilisateur, String courriel, String mdp)
          throws IOException, SQLException, Exception {
    User u = userValidation(nomUtilisateur, courriel, mdp);
    if (u != null || !chargerUserData()) {
      //"Compte déjà existant"
      initialise = false;
      return false;
    }
    setUsagerActif(u);
    // Utiliser le gestionnaire utilisateur pour sauvegarder ce nouveau avec som mdp

    chargerUserData();
    initialise = true;
    return true;
  }

  /**
   *  
   * 
   * @param nomUtilisateur 
   * @param courriel 
   * @param mdp 
   * @return 
   * @throws IOException 
   * @throws SQLException 
   * @throws Exception  
   */
  public boolean initExistant(String nomUtilisateur, String courriel, String mdp)
          throws IOException, SQLException, Exception {
    User u = userValidation(nomUtilisateur, courriel, mdp);
    if (u == null || !u.isMdp(mdp) || !chargerUserData()) {
      initialise = false;
      return false;
    }

    if (!chargerUserData()) {
      return false;
    }
    
    initialise = true;
    return true;
  }

  /**
   * Pas encore fini
   * 
   * @return
   * @throws Exception 
   */
  public static boolean chargerUserData() throws Exception {
    if (usagerActif == null || userValidation(usagerActif) == null) {
      G_Validation.addMessageErreur("L'utilisateur n'a pas encore été initialisé.");
      return false;
    }
    
    Connection conn = SimpleDataSource.getConnection();
    ObservableList items = FXCollections.observableArrayList();
    try {
      
      int userId = usagerActif.getId();
      int gbId;
      int bmId;
      /*
      PreparedStatement stat = conn.prepareStatement(
              "(SELECT id_groupBook FROM user_group WHERE id_user = '" + userId + "')");
      ResultSet rs = stat.executeQuery();
      
      while (rs.next()) {
          gbId = rs.getInt(1);
          PreparedStatement stat2 = conn.prepareStatement(
                  "(SELECT id_bookmark FROM bookmark_group WHERE id_group = '" + gbId + "')");
          
          ResultSet rs2 = stat2.executeQuery();
          while (rs2.next()) {
            bmId = rs2.getInt(1);
            contenus
          }
          String query3 = "SELECT nom_site, Description, Url "
                  + "FROM bookmark "
                  + "WHERE id = ? ";
          
          PreparedStatement ps3 = conn.prepareStatement(query3);
          ps3.setInt(1, bmId);
          
          ResultSet rs3 = ps3.executeQuery();
          String name = null;
          if (rs3.next()) {
              txt_file_name.setText(rs3.getString(1));
              txt_adress.setText(rs3.getString(3));
          }
          
          items.add(rs3.getString(1));
          list_mp.setItems(items);
      }
      */
    } finally {
        conn.close();
    }
    
    return true;
  }

  /**
   *
   * @param user
   * @return
   */
  public static boolean addUser(User user) throws SQLException, IOException {
    Groupbook gb;
    if (containsLeft(acces, user)) {
      return false;
    } else {
      G_GB.add(user);
      gb = new Groupbook(0, user.getNom(), user.getCourriel(), 0, new ArrayList<>(),
              new ArrayList<>(), identifiants);
      //acces.add(u, gb);
      return true;
    }
    //users.add(u);
  }
  
  public static boolean validUserConnexion(String nom, String mdp) {
    return G_Validation.validUserConnexion(nom, mdp);
  }

  public void setUsagerActif(User u)
          throws IOException, SQLException {
    usagerActif = userValidation(u);
  }

  public User getUsagerActif() {
    return usagerActif;
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
   * @param i 
   * @throws java.io.IOException 
   */
  public void addId(int i) throws IOException {
    if (i < 1) {
      throw new IOException("Identifiant invalide");
    }
    identifiants.add(i);
  }

  public Groupbook getUserRacineGroupbook(User user)
          throws Exception {
    int id = user.getRacine();
    for (Groupbook gb : Recherche.getRights(user, acces, groupbooks)) {
      if (id == gb.getId()) {
        return gb;
      }
    }
    return null;
  }

  public ArrayList<Groupbook> getUserGroupbooks(User user)
          throws Exception {
    return getChildGroupbooks(getUserRacineGroupbook(user));
  }

  public ArrayList<Groupbook> getChildGroupbooks(Groupbook parent)
          throws IOException, SQLException {
    /*if (null == G_Validation.userValidation(user)) {
    throw new IOException("Utilisateur mal initialisé");
    }*/
    return Recherche.getRights(parent, conteneurs, groupbooks);
  }
  
  public void addMessageErreur(String message) {
    G_Validation.addMessageErreur(message);
  }
  
  public String getMessageErreur() {
    return G_Validation.getMessageErreur();
  }
  
  public void addMessageConfirmation(String message) {
    G_Validation.addMessageConfirmation(message);
  }
  
  public String getMessageConfirmation() {
    return G_Validation.getMessageConfirmation();
  }
  
  public boolean estEnErreur() {
    return G_Validation.estEnErreur();
  }
  
  public void estEnErreur(boolean etat) {
    G_Validation.estEnErreur(etat);
  }
  
  public boolean valideUtilisateur(String nomUtilisateur, String courriel, String mdp) throws IOException, SQLException {
    return null != G_Validation.userValidation(nomUtilisateur, courriel, mdp);
  }

  /**
   * GETTEURS ET SETTEURS DE BASE ***************************
   */
  
  /**
   * Obtien la table d'association (TA) qui lie les utilisateurs à leurs
   *
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
    Gestionnaire.acces = acces;
  }

  public void setConteneurs(TA_GB_GB conteneurs) {
    Gestionnaire.conteneurs = conteneurs;
  }

  public void setContenus(TA_GB_BM contenus) {
    Gestionnaire.contenus = contenus;
  }

  public void setEtiquettes(TA_BM_Tag etiquettes) {
    Gestionnaire.etiquettes = etiquettes;
  }

  public void setUsers(ArrayList<User> users) {
    Gestionnaire.users = users;
  }

  public void setGroupbooks(ArrayList<Groupbook> groupbooks) {
    Gestionnaire.groupbooks = groupbooks;
  }

  public void setBookmarks(ArrayList<Bookmark> bookmarks) {
    Gestionnaire.bookmarks = bookmarks;
  }

  public void setTags(ArrayList<Tag> tags) {
    Gestionnaire.tags = tags;
  }

  public void setIdentifiants(ArrayList<Integer> identifiants) {
    Gestionnaire.identifiants = identifiants;
  }
}
