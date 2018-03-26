/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;
import sqlclass.SimpleDataSource;

/**
 *
 * @author olivi
 */
public class G_Validation {

  private static final int MAX_VAL_TYPE_PARTAGE = 2;
  private static boolean erreur = false;
  private static ArrayList<String> messagesErreur = null;
  private static Connection conn = null;

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

  private static boolean nn(String text) {
    return text != null;
  }

  private static char premierChar(String text) {
    return text.charAt(0);
  }

  private static char dernierChar(String text) {
    return text.charAt(text.length() - 1);
  }

  private static boolean chaineMinime(String text) {
    if (nn(text)) {
      return false;
    }

    String min = alphaSeulement(text);
    return !(min.isEmpty() || "".equals(min));
  }

  private static boolean chaineValide(String text, int maximum) {
    return chaineMinime(text) && text.length() > 5 && text.length() <= maximum;
  }

  private static String alphaSeulement(String text) {
    return text.replaceAll("[^A-Za-z]", "");
  }

  public static boolean courriel(String adresse) {
    return EmailValidator.getInstance().isValid(adresse);
  }

  public static boolean nom(String nom) {
    return !chaineValide(nom, 20);
  }

  public static boolean mdp(String mdp) {
    return !chaineValide(mdp, 20);
  }

  public static boolean url(String url) {
    String[] schemes = {"http", "https"};
    UrlValidator urlValidator = new UrlValidator(schemes);
    return urlValidator.isValid(url);
  }

  public static boolean gbRacine(Groupbook racine) throws Exception {
    return !(!gb(racine) || racine.getTypePartage() != 0
            || racine.getParent() != racine.getId()
            || !"racine".equals(racine.getNom()));
  }

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

  public static User userValidation(User user)
          throws IOException, SQLException {
    return userValidation(user.getNom(), user.getCourriel(), "mot de passe");
  }

  /**
   * 
   * @param nomTable
   * @param nomCol
   * @param valCol
   * @param text
   * @return 
   * @throws SQLException
   * @throws IOException
   */
  public static boolean compareColString(String nomTable, String nomCol, int valCol, String text)
          throws SQLException, IOException {
    conn = SimpleDataSource.getConnection();
    try {
      if (text == null) {
        throw new IOException();
      }
      Statement stat = conn.createStatement();
      ResultSet rs = stat.executeQuery("SELECT " + nomCol + " FROM " + nomTable + " WHERE id = " + valCol);
      rs.next();
      if (text.equals(rs.getString(1))) {
        return true;
      }
    } 
    catch (IOException | SQLException e) {
      return false;
    } 
    finally {
      conn.close();
    }
    return false;
  }
  
  public static void addMessageErreur(String message) {
    G_Validation.erreur = true;
    messagesErreur.add(message);
  }
  
  public static String getMessageErreur() {
    if (!estEnErreur()) return "";
    StringBuilder message = new StringBuilder();
    for (String e : messagesErreur) {
      message.append(e);
      message.append(System.getProperty("line.separator"));
    }
    G_Validation.erreur = false;
    return message.toString();
  }
  
  public static boolean estEnErreur() {
    return erreur;
  }
}
