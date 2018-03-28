/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import sqlclass.SimpleDataSource;

/**
 * Classe qui s'occupe de la gestion des validations de saisies et de
 * correspondance avec la BD
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class G_Validation {

  private static final int MAX_VAL_TYPE_PARTAGE = 2;
  private static boolean erreur = false;
  private static ArrayList<String> messagesErreur = new ArrayList<>();
  private static ArrayList<String> messagesConfirmation = new ArrayList<>();
  private static ArrayList<String> nomColsConnexionTemp = new ArrayList<>();
  private static Connection conn = null;

  /**
   * Constructeur de la classe
   */
  public G_Validation() {
    nomColsConnexionTemp = new ArrayList<>();
    nomColsConnexionTemp.add("user_name");
    nomColsConnexionTemp.add("user_password");
  }

  /**
   * Compte le nombre d'occurence d'une chaine de caractère dans une autre
   *
   * @param element Élement recherché
   * @param text Chaine de caractères
   * @return Nombre de fois qu'on retrouve l'élément dans la chaine de caractère
   */
  private static int nbOccurence(String element, String text) {
    return text.length() - text.replace(element, "").length();
  }

  /**
   * Valide que la chaine ne soit pas nulle ou vide
   *
   * @param text Chaine de caractère
   * @return Vrai si la chaine de caractère n'est pas vide.
   */
  private static boolean nn(String text) {
    return text != null && !text.isEmpty();
  }

  /**
   * Obtient le premier caractère de la chaine
   *
   * @param text Chaine de caractère
   * @return Premier caractère de la chaine
   */
  private static char premierChar(String text) {
    return text.charAt(0);
  }

  /**
   * Obtient le dernier caractère de la chaine
   *
   * @param text Chaine de caractère
   * @return Dernier caractère de la chaine
   */
  private static char dernierChar(String text) {
    return text.charAt(text.length() - 1);
  }

  /**
   * Valide que la chaine de caractère contient des caractères alphabétique
   *
   * @param text Chaine de caractère
   * @return Vrai si la chaine de caractère contiens assez de caractère
   * alphabétique
   */
  private static boolean chaineMinime(String text) {
    if (nn(text)) {
      return false;
    }

    String min = alphaSeulement(text);
    return !(min.isEmpty() || "".equals(min));
  }

  /**
   * Valide qu'une chaine de caractère contient assez de caractères alphabétique
   *
   * @param text Chaine de caractère à valider
   * @param maximum Nombre maximal de caractère que la chaine de caractère peut
   * contenir
   * @return Vrai si la chaine contient assez de caractères alphabétique
   */
  private static boolean chaineValide(String text, int maximum) {
    return chaineMinime(text) && text.length() > 5 && text.length() <= maximum;
  }

  /**
   * Enlève toutes les caractères non alphabétique d'une chaine
   *
   * @param text Chaine de caractère à modifier
   * @return La chaine modifiée
   */
  private static String alphaSeulement(String text) {
    return text.replaceAll("[^A-Za-z]", "");
  }

  /**
   *
   *
   * @param adresse
   * @return
   */
  public static boolean courriel(String adresse) {
    return EmailValidator.getInstance().isValid(adresse);
  }

  /**
   *
   * @param nom
   * @return
   */
  public static boolean nom(String nom) {
    return !chaineValide(nom, 20);
  }

  /**
   *
   * @param mdp
   * @return
   */
  public static boolean mdp(String mdp) {
    return !chaineValide(mdp, 20);
  }

  /**
   *
   * @param url
   * @return
   */
  public static boolean url(String url) {
    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes);
    return urlValidator.isValid(url);
  }

  /**
   *
   * @param racine
   * @return
   * @throws Exception
   */
  public static boolean gbRacine(Groupbook racine) throws Exception {
    return !(!gb(racine) || racine.getTypePartage() != 0
            || racine.getParent() != racine.getId()
            || !"racine".equals(racine.getNom()));
  }

  /**
   *
   * @param gb
   * @return
   * @throws Exception
   */
  public static boolean gbPartage(Groupbook gb) throws Exception {
    return !(!gb(gb) || gb.getTypePartage() == 0
            || gb.getParent() == gb.getId());
  }

  private static boolean gb(Groupbook gb) throws Exception {
    if (gb == null || chaineMinime(gb.getNom())) {
      return false;
    }
    int type = gb.getTypePartage();
    return !(type < 0 || type > MAX_VAL_TYPE_PARTAGE);
  }

  /**
   *
   * @param nomUtilisateur
   * @param courriel
   * @param mdp
   * @return
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   * @throws SQLException Connexion à la base de donnée incomplète
   */
  public static User userValidation(String nomUtilisateur, String courriel, String mdp)
          throws IOException, SQLException {
    if (!G_Validation.nom(nomUtilisateur)) {
      addMessageErreur("Le nom de l'utilisateur doit comporter entre 6 et 20 caractères.");
    }
    if (!G_Validation.courriel(courriel)) {
      addMessageErreur("Le format du courriel saisie n'est pas valide.");
    }
    if (!G_Validation.mdp(mdp)) {
      addMessageErreur("Le mot de passe saisie ne respecte pas les critères. Il doit comporter entre 6 et 20 caractères.");
    }
    return User.recherche(nomUtilisateur, courriel);
  }

  /**
   *
   * @param nomUtilisateur
   * @param mdp
   * @return
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   * @throws SQLException Connexion à la base de donnée incomplète
   */
  public static User userValidation(String nomUtilisateur, String mdp)
          throws IOException, SQLException {
    // Faire ces validations dans le gestionnaire des utilisateurs 
    // lors de l'initialisation des nouveaux (G_User) ?
    if (!G_Validation.nom(nomUtilisateur)) {
      throw new IOException("Le nom de l'utilisateur doit comporter entre 6 et 20 caractères.");
    }
    if (!G_Validation.mdp(mdp)) {
      throw new IOException("Le mot de passe saisie ne respecte pas les critères. Il doit comporter entre 6 et 20 caractères.");
    }
    return User.recherche(nomUtilisateur, mdp);
  }

  /**
   * Valide les informations
   *
   * @param user Utilisateur à valider
   * @return Vrai si l'instance de l'utilisateur correspond à un des
   * enregistrements de la DB
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   * @throws SQLException Connexion à la base de donnée incomplète
   */
  public static User userValidation(User user)
          throws IOException, SQLException {
    return userValidation(user.getNom(), user.getCourriel(), "mot de passe");
  }

  /**
   * Valide la connexion d'un utilisateur à l'aide de la BD
   *
   * @param nom Nom de l'utilisateur
   * @param mdp Mot de passe
   * @return Vrai si les iformation de l'utilisateur saisie correspond à un des
   * enregistrements de la DB
   */
  public static boolean validUserConnexion(String nom, String mdp) {
    ArrayList<Object> valRechCols = new ArrayList<>();
    valRechCols.add(nom);
    valRechCols.add(mdp);
    ArrayList<String> nomColsConnexion = new ArrayList<>();
    nomColsConnexion.add("user_name");
    nomColsConnexion.add("user_password");
    try {
      return compareBD("user", nomColsConnexion, valRechCols);
    } catch (IOException e) {
      G_Validation.addMessageErreur("Les données saisies ne sont pas dans un bon format.");
    } catch (SQLException e) {
      G_Validation.addMessageErreur("Une erreur s'est produite qui empêche de lire dans la BD.");
    }
    return false;

  }

  /**
   *
   * @param nomTable
   * @param nomCol
   * @param valCol
   * @param text
   * @return Vrai si le contenu de la colone dans la BD est égale à la valeur
   * saisie
   * @throws SQLException Connexion à la base de donnée incomplète
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   */
  public static boolean compareColString(String nomTable, String nomCol, int valCol, String text)
          throws SQLException, IOException {
    conn = SimpleDataSource.getConnection();
    try {
      if (nomTable == null || nomCol == null || text == null) {
        throw new IOException();
      }
      Statement stat = conn.createStatement();
      ResultSet rs = stat.executeQuery("SELECT " + nomCol + " FROM " + nomTable + " WHERE id = " + valCol);
      rs.next();
      if (text.equals(rs.getString(1))) {
        return true;
      }
    } catch (IOException | SQLException e) {
      return false;
    } finally {
      conn.close();
    }
    return false;
  }

  private static String makeFetch(ArrayList<String> nomCols) {
    StringBuilder fetch = new StringBuilder();
    fetch.append(nomCols.get(0));
    for (int i = 1; i < nomCols.size(); i++) {
      fetch.append(", ").append(nomCols.get(i));
    }
    return fetch.toString();
  }

  private static String makeConditions(ArrayList<String> valCols, ArrayList<Object> valRechCols) {
    StringBuilder condition = new StringBuilder();
    condition.append(valCols.get(0)).append(" = ").append(valRechCols.get(0));
    for (int i = 1; i < valCols.size(); i++) {
      condition.append(" AND ").append(valCols.get(i)).append(" = ").append(valRechCols.get(i).toString());
    }
    return condition.toString();
  }

  /**
   *
   * @param nomTable
   * @param nomCols
   * @param valRechCols
   * @return
   * @throws SQLException Connexion à la base de donnée incomplète
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   */
  private static boolean compareBD(String nomTable,
          ArrayList<String> nomCols, ArrayList<Object> valRechCols)
          throws SQLException, IOException {
    conn = SimpleDataSource.getConnection();
    try {
      if (nomTable == null || nomCols == null || valRechCols == null
              || nomCols.size() != valRechCols.size()) {
        throw new IOException();
      }
      Statement stat = conn.createStatement();
      ResultSet rs = stat.executeQuery("SELECT "
              + makeFetch(nomCols) + " FROM " + nomTable
              + " WHERE " + makeConditions(nomCols, valRechCols));

      rs.next();
      for (int i = 0; i < valRechCols.size(); i++) {
        if (!compareRS(rs, valRechCols.get(i), nomCols.get(i))) {
          return false;
        }
      }

    } catch (IOException | SQLException e) {
      return false;
    } finally {
      conn.close();
    }
    return true;
  }

  /**
   *
   * @param rs
   * @param val
   * @param nomCol
   * @return
   * @throws SQLException Connexion à la base de donnée incomplète
   */
  private static boolean compareRS(ResultSet rs, Object val, String nomCol) throws SQLException {
    if (val instanceof String) {
      if (val.equals(rs.getString(nomCol))) {
        return true;
      }
    } else if (val instanceof Integer) {
      if (val.equals(rs.getInt(nomCol))) {
        return true;
      }
    } else if (val instanceof Float) {
      if (val.equals(rs.getFloat(nomCol))) {
        return true;
      }
    }
    return false;
  }

  /**
   *
   * @param message
   */
  public static void addMessageErreur(String message) {
    G_Validation.erreur = true;
    G_Validation.messagesErreur.add(message);
  }

  /**
   *
   * @param message
   */
  public static void addMessageConfirmation(String message) {
    G_Validation.messagesConfirmation.add(message);
  }

  /**
   *
   *
   * @param name
   * @param mdp
   * @return
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   * @throws SQLException Connexion à la base de donnée incomplète
   * @throws ClassNotFoundException
   */
  public static boolean validUser(String name, String mdp)
          throws IOException, SQLException, ClassNotFoundException {

    return validName(name) == true && validPassword(name, mdp) == true;
  }

  /**
   *
   *
   * @param name
   * @return
   * @throws SQLException Connexion à la base de donnée incomplète
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   */
  public static boolean validName(String name)
          throws SQLException, IOException {

    return G_User.getUserId(name).getId() > 0;
  }

  /**
   *
   *
   * @param name
   * @param mdp
   * @return
   * @throws IOException Les champs saisies ne correspond pas au format requis
   * pour la validation
   * @throws SQLException Connexion à la base de donnée incomplète
   * @throws ClassNotFoundException
   */
  public static boolean validPassword(String name, String mdp)
          throws IOException, SQLException, ClassNotFoundException {

    conn = SimpleDataSource.getConnection();
    try {

      PreparedStatement stat = conn.prepareStatement(
              "(SELECT user_password "
              + "FROM user "
              + "WHERE user.user_name = '" + name + "')");

      ResultSet rs = stat.executeQuery();
      String pass;

      if (rs.next()) {
        pass = rs.getString(1);
        if (!pass.equals(mdp)) {
          return false;

        }
      }

    } finally {
      conn.close();

    }

    return true;
  }

  /**
   *
   * @return
   */
  public static String getMessageErreur() {
    String message = "";
    if (estEnErreur()) {
      message = getMessage(G_Validation.messagesErreur);
      G_Validation.messagesErreur.clear();
    }
    return message;
  }

  /**
   *
   * @return
   */
  public static String getMessageConfirmation() {
    String message = "";
    if (estEnErreur()) {
      message = getMessage(G_Validation.messagesConfirmation);
      G_Validation.messagesConfirmation.clear();
    }
    return message;
  }

  private static String getMessage(ArrayList<String> messages) {
    final String br = System.getProperty("line.separator");
    StringBuilder message = new StringBuilder();
    if (messages.size() == 1) {
      message.append(messages.get(0));
    } else {
      for (int i = 0; i < messages.size(); i++) {
        message.append("No ").append(i + 1).append(" : ").append(messages.get(i)).append(br);
      }
    }
    G_Validation.erreur = false;
    return message.toString();
  }

  /**
   *
   * @return
   */
  public static boolean estEnErreur() {
    return G_Validation.erreur;
  }

  /**
   *
   * @param etat
   */
  public static void estEnErreur(boolean etat) {
    G_Validation.erreur = etat;
  }
}
