/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import static applicationclass.G_Validation.*;
import static applicationclass.Recherche.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    return true;
  }

  /**
   * Pas encore fini
   *
   * @return
   * @throws Exception
   */
  public boolean chargerUserData() throws Exception {
    if (usagerActif == null || userValidation(usagerActif) == null) {
      G_Validation.addMessageErreur("L'utilisateur n'a pas encore été initialisé.");
      return false;
    }

    Connection conn = SimpleDataSource.getConnection();
    try {

      int userId = usagerActif.getId();
      int gbNb = 0;
      int gbId;
      int bmId;
      int tagId;

      Statement stat_user_gb = conn.createStatement();
      ResultSet rs_user_gb = stat_user_gb.executeQuery(
              "(SELECT id_groupBook FROM user_group WHERE id_user = '" + userId + "')");

      PreparedStatement stat_gb = conn.prepareStatement(
              "(SELECT * FROM group WHERE id = ?)");
      ResultSet rs_gb;
      /*@old-node_suggestion ne prendre seullement les gb qui sont possédés par l'utilisateur*/
      PreparedStatement stat_gb_bm = conn.prepareStatement(
              "(SELECT id_bookmark FROM bookmark_group WHERE id_group = ?)");
      ResultSet rs_gb_bm;
      PreparedStatement stat_bm = conn.prepareStatement(
              "(SELECT * FROM bookmark WHERE id = ?)");
      ResultSet rs_bm;
      PreparedStatement stat_bm_tag = conn.prepareStatement(
              "(SELECT id_tag FROM bookmark_tag WHERE id_bookmark = ?)");
      ResultSet rs_bm_tag;
      PreparedStatement stat_tag = conn.prepareStatement(
              "(SELECT * FROM tag WHERE id = ?)");
      ResultSet rs_tag;

      acces.clear();
      conteneurs.clear();
      contenus.clear();
      etiquettes.clear();

      users.clear();
      groupbooks.clear();
      bookmarks.clear();
      tags.clear();

      /*@old-node_question Méthode pour marquer la racine. Valide? */
      if (rs_user_gb.next()) {
        conteneurs.add(new DBA(1, rs_user_gb.getInt(1), rs_user_gb.getInt(1)));
      }
      rs_user_gb.previous();

      /*Pour tous les gb accessibles par l'utilisateur*/
      while (rs_user_gb.next()) {
        gbNb++;
        gbId = rs_user_gb.getInt(1);
        /*@old-node_question Ne pas inclure la racine dans acces pour prévenir les recherches en boucles? */
        acces.add(new DBA(1, userId, gbId));

        stat_gb.setInt(1, gbId);
        rs_gb = stat_gb.executeQuery();
        rs_gb.next();
        groupbooks.add(G_GB.initGroupbook(rs_gb, gbId));

        /*@old-node_debut_suggestion Pout tout les enfants du gb .... récursivement */
        stat_gb_bm.setInt(1, gbId);
        rs_gb_bm = stat_gb_bm.executeQuery();

        /*Pour tout les bm contenus dans le gb*/
        while (rs_gb_bm.next()) {
          bmId = rs_gb_bm.getInt(1);
          contenus.add(new DBA(1, gbId, bmId));

          stat_bm.setInt(1, bmId);
          rs_bm = stat_bm.executeQuery();
          rs_bm.next();
          bookmarks.add(new Bookmark(rs_bm));

          stat_bm_tag.setInt(1, bmId);
          rs_bm_tag = stat_bm_tag.executeQuery();

          /*Pour tout les tags placés sur le bm*/
          while (rs_bm_tag.next()) {
            tagId = rs_bm_tag.getInt(1);
            etiquettes.add(new DBA(1, bmId, tagId));

            stat_tag.setInt(1, tagId);
            rs_tag = stat_tag.executeQuery();
            rs_tag.next();
            tags.add(new Tag(rs_tag));
          }
        }
        /*@old-node_fin_suggestion*/
      }
      // Vérifier si les données sont justes...
      if (gbNb != groupbooks.size()) {
        System.out.println("Erreur : Le nombre de groupbook lues ne semble pas adéquat à ceux dans la BD.");
      }

    } catch (SQLException e) {
      addMessageErreur("Connexion avec la BD incomplête.");
      return false;
      /* changer le return final au lieu */

    } finally {
      conn.close();

    }

    return true;
  }

  /**
   *
   * @param user
   * @return
   * @throws java.sql.SQLException
   * @throws java.io.IOException
   */
  public static boolean addUser(User user) throws SQLException, IOException {
    int id = 1;
    Groupbook gb;
    if (containsLeft(acces, user)) {
      return false;
    } else {
      G_GB.add(user);
      gb = new Groupbook(0, user.getNom(), user.getCourriel(), 0, new ArrayList<>(),
              new ArrayList<>(), identifiants);
      acces.add(new DBA(id, user, gb));
    }
    users.add(user);
    return true;
  }

  /**
   *
   * @param nom
   * @param mdp
   * @return
   */
  public static boolean validUserConnexion(String nom, String mdp) {
    return G_Validation.validUserConnexion(nom, mdp);
  }

  /**
   *
   * @param u
   * @throws IOException
   * @throws SQLException
   */
  public void setUsagerActif(User u)
          throws IOException, SQLException {
    usagerActif = u;
  }

  /**
   *
   * @return
   */
  public User getUsagerActif() {
    return usagerActif;
  }

  /**
   *
   * @throws SQLException
   */
  public void loadUserGb() throws SQLException {
    Connection conn = SimpleDataSource.getConnection();
    groupbooks.clear();
    groupbooks.add(new Groupbook());

    PreparedStatement stat = conn.prepareStatement(
            "(SELECT id_groupBook FROM user_group WHERE id_user = '" + getUsagerActif().getId() + "')");

    ResultSet rs = stat.executeQuery();
    int id_gp = getUsagerActif().getId();

    Bookmark bm = new Bookmark();

    while (rs.next()) {
      id_gp = rs.getInt(1);
      groupbooks.get(groupbooks.size() - 1).setId(id_gp);

      PreparedStatement stat2 = conn.prepareStatement(
              "(SELECT id_bookmark FROM bookmark_group WHERE id_group = '" + id_gp + "')");

      ResultSet rs2 = stat2.executeQuery();

      while (rs2.next()) {
        int id_bm = rs.getInt(1);
        bm.setId(id_bm);
        contenus.add(new DBA<>(id_gp, groupbooks.get(groupbooks.size() - 1), bm));

        String query3 = "SELECT nom_site, Description, Url "
                + "FROM bookmark "
                + "WHERE id = ? ";

        PreparedStatement ps3 = conn.prepareStatement(query3);
        ps3.setInt(1, id_bm);

        ResultSet rs3 = ps3.executeQuery();

        String name = null;
        if (rs3.next()) {
          bm.setNom(rs3.getString(1));
          bm.setDescription(rs3.getString(3));
          bookmarks.add(bm);
//                    groupbooks.get(groupbooks.size()-1).addBookmark(bm.getId());
        }
      }

    }

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

  /**
   *
   * @param user
   * @return
   * @throws Exception
   */
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

  /**
   *
   * @param user
   * @return
   * @throws Exception
   */
  public ArrayList<Groupbook> getUserGroupbooks(User user)
          throws Exception {
    return getChildGroupbooks(getUserRacineGroupbook(user));
  }

  /**
   *
   * @param parent
   * @return
   * @throws IOException
   * @throws SQLException
   */
  public ArrayList<Groupbook> getChildGroupbooks(Groupbook parent)
          throws IOException, SQLException {
    /*if (null == G_Validation.userValidation(user)) {
  throw new IOException("Utilisateur mal initialisé");
  }*/
    return Recherche.getRights(parent, conteneurs, groupbooks);
  }

  /**
   *
   * @param message
   */
  public void addMessageErreur(String message) {
    G_Validation.addMessageErreur(message);
  }

  /**
   *
   * @return
   */
  public String getMessageErreur() {
    return G_Validation.getMessageErreur();
  }

  /**
   *
   * @param message
   */
  public void addMessageConfirmation(String message) {
    G_Validation.addMessageConfirmation(message);
  }

  /**
   *
   * @return
   */
  public String getMessageConfirmation() {
    return G_Validation.getMessageConfirmation();
  }

  /**
   *
   * @return
   */
  public boolean aUneConfirmation() {
    String resultat = getMessageConfirmation();
    return !("".equals(resultat) || resultat.isEmpty());
  }

  /**
   *
   * @return
   */
  public boolean estEnErreur() {
    String resultat = getMessageErreur();
    if ("".equals(resultat) || resultat.isEmpty()) {
      return false;
    }
    return G_Validation.estEnErreur();
  }

  /**
   *
   * @param etat
   */
  public void estEnErreur(boolean etat) {
    G_Validation.estEnErreur(etat);
  }

  /**
   *
   * @param nomUtilisateur
   * @param courriel
   * @param mdp
   * @return
   * @throws IOException
   * @throws SQLException
   */
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

  /**
   *
   * @return
   */
  public TA_GB_GB getConteneurs() {
    return conteneurs;
  }

  /**
   *
   * @return
   */
  public TA_GB_BM getContenus() {
    return contenus;
  }

  /**
   *
   * @return
   */
  public TA_BM_Tag getEtiquettes() {
    return etiquettes;
  }

  /**
   *
   * @return
   */
  public ArrayList<User> getUsers() {
    return users;
  }

  /**
   *
   * @return
   */
  public ArrayList<Groupbook> getGroupbooks() {
    return groupbooks;
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
  public ArrayList<Groupbook> getCurrentGroupbooks() { // throws SQLException, Exception
    return groupbooks; //usagerActif.getOwnedGroupbooks()
  }

  /**
   *
   * @return
   */
  public ArrayList<Tag> getTags() {
    return tags;
  }

  /**
   *
   * @return
   */
  public ArrayList<Integer> getIdentifiants() {
    return identifiants;
  }

  /**
   *
   * @param acces
   */
  public void setAcces(TA_User_GB acces) {
    Gestionnaire.acces = acces;
  }

  /**
   *
   * @param conteneurs
   */
  public void setConteneurs(TA_GB_GB conteneurs) {
    Gestionnaire.conteneurs = conteneurs;
  }

  /**
   *
   * @param contenus
   */
  public void setContenus(TA_GB_BM contenus) {
    Gestionnaire.contenus = contenus;
  }

  /**
   *
   * @param etiquettes
   */
  public void setEtiquettes(TA_BM_Tag etiquettes) {
    Gestionnaire.etiquettes = etiquettes;
  }

  /**
   *
   * @param users
   */
  public void setUsers(ArrayList<User> users) {
    Gestionnaire.users = users;
  }

  /**
   *
   * @param groupbooks
   */
  public void setGroupbooks(ArrayList<Groupbook> groupbooks) {
    Gestionnaire.groupbooks = groupbooks;
  }

  /**
   *
   * @param bookmarks
   */
  public void setBookmarks(ArrayList<Bookmark> bookmarks) {
    Gestionnaire.bookmarks = bookmarks;
  }

  /**
   *
   * @param tags
   */
  public void setTags(ArrayList<Tag> tags) {
    Gestionnaire.tags = tags;
  }

  /**
   *
   * @param identifiants
   */
  public void setIdentifiants(ArrayList<Integer> identifiants) {
    Gestionnaire.identifiants = identifiants;
  }
}
