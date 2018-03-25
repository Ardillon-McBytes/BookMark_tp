/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import static applicationclass.G_Validation.nom;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
  private static String[] messagesErreur = null;
  private static Connection conn = null;

  public static void main(String[] args) {
    if (!G_Validation.url("https://www.google.com/")) {
      System.out.println("Erreur");
    }
    else {
      System.out.println("Correct");
    }
    
    String[] courriels = {"mon@email.com", "mon@e.mai.l.com", "m.o.n@email.com",
      "", "mon.@email.com", "@email.com", "mon@.com", "mon@email.",
      "mon@email.com.", ".mon@email.com", "mon@com"};

    for (String c : courriels) {
      System.out.println(c + " est " + G_Validation.courriel(c));
    }

    String[] noms = {"", "...", "o.o",
      "Patate", "Bal-Tazard-", "&?%$&*?", "mµnicipalité", "(a)()(",
      "o-.", "asdjaw.", ".oipoi"};

    for (String n : noms) {
      System.out.println(n + " est " + G_Validation.nom(n));
    }
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

    /*// Méthode avec JavaMail (Library javax.mail.jar)
    boolean result = true;
    try {
      InternetAddress emailAddr = new InternetAddress(adresse);
      emailAddr.validate();
    } catch (AddressException ex) {
      result = false;
    }
    return result;*/
 /*if (adresse == null || adresse.isEmpty() || 
            !adresse.contains(".") || 1 != nbOccurence("@", adresse)) {
      return false;
    }
    
    String[] aCommercial = adresse.split("@");
    String[] point = aCommercial[aCommercial.length - 1].split("\\.");
    String debut = alphaSeulement(aCommercial[0]);
    String fin = alphaSeulement(point[point.length - 1]);
    if (dernierChar(debut) != dernierChar(aCommercial[0]) || 
            premierChar()) {
      return false;
    }
    return (aCommercial.length < 2 || point.length < 2 || 
            !(debut.isEmpty() || premierChar(adresse) != premierChar(debut)) && 
            !(fin.isEmpty() || dernierChar(adresse) != dernierChar(fin)));*/
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

    //return !(url == null || url.isEmpty());
    /*if (!url.toLowerCase().startsWith("http") || 
            "".equals(alphaSeulement(url))) {
      return false;
    }
    return true;*/
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
    // Faire ces validations dans le gestionnaire des utilisateurs 
    // lors de l'initialisation des nouveaux (G_User) ?
    if (!G_Validation.nom(nomUtilisateur)) {
      throw new IOException("Le nom de l'utilisateur doit comporter entre 6 et 20 caractères.");
    }
    if (!G_Validation.courriel(courriel)) {
      throw new IOException("Le format du courriel saisie n'est pas valide.");
    }
    if (!G_Validation.mdp(mdp)) {
      throw new IOException("Le mot de passe saisie ne respecte pas les critères. Il doit comporter entre 6 et 20 caractères.");
    }
    return User.recherche(nomUtilisateur, courriel);
  }
  
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
  
   public static boolean validUser(String name,String mdp) throws IOException, SQLException, ClassNotFoundException
    {
        if (validName(name) == true &&
                validPassword(name,mdp) == true) {
      
            return true;
            
        }
        return false;
    }
  public static boolean validName(String name) throws SQLException, IOException {
     
        if ( G_User.getUserId(name) > 0) {
            return true;
        }
        return false;

    }

  public static boolean validPassword(String name, String mdp) throws IOException, SQLException, ClassNotFoundException {
        Connection conn = SimpleDataSource.getConnection();
        try {

            PreparedStatement stat = conn.prepareStatement(
                    "(SELECT user_password "
                    + "FROM user "
                    + "WHERE user.user_name = '" + name + "')");

            ResultSet rs = stat.executeQuery();
            String pass = null;

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
  
  public static String[] getMessagesErreur() {
    return messagesErreur;
  }
}
