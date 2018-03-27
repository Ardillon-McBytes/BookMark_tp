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
import sqlclass.SimpleDataSource;

/**
 * Classe pour les utilisateurs
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class User implements DBField {

    // Utiliser GestionnaireUser pour le chargement opérer dessus celui-ci
    private static int count = 0;
    private int id;
    private String nom;
    private String courriel;
    private int groupbookRacine;
    private Connection conn;  // metre toujours en static?

    /**
     * Constructeur sans paramètres de la classe des utilisateurs
   * @param name
     */
    public User(String name) {
        nom = name;
    }

  /**
   *
   * @param id
   */
  public User(int id) {
        this.id = id;
    }

  /**
   *
   */
  public User() {
        this.id = -1;
        this.nom = "";
        this.courriel = "";
        this.groupbookRacine = -1;
        this.conn = null;
    }

  /**
   *
   * @param id
   * @param nom
   * @param courriel
   * @param groupbookRacine
   * @throws IOException
   */
  public User(int id, String nom, String courriel, int groupbookRacine) throws IOException {
        this.id = id;
        this.nom = nom;
        this.courriel = courriel;
        User.this.setGroupbooks(groupbookRacine);
        this.conn = null;
    }

    /**
     * Constructeur de la classe des utilisateurs
     *
     * @param id Identifiant
     * @param nom Nom du compte
     * @param courriel Mot de passe
     * @param groupBooks Liste des dossiers que l'utilisateur possède
     * @param ta Table d'association (TA) où l'on retrouve les dossiers des
     * utilisateurs
     */
    public User(int id, String nom, String courriel,
            ArrayList<Groupbook> groupBooks, TA_User_GB ta) {
        this.id = id;
        this.nom = nom;
        this.courriel = courriel;
        User.this.setGroupbooks(ta, groupBooks);
        this.conn = null;
    }

  /**
   *
   * @return
   * @throws Exception
   */
  public int getRacine() throws Exception {
        if (this.groupbookRacine < 1) {
            throw new Exception("Utilisateur pas encore initialisé");
        }
        return this.groupbookRacine;
    }

  /**
   *
   * @param nom
   * @param courriel
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static User recherche(String nom, String courriel) throws SQLException, IOException {
        // Utiliser cette méthode dans le gestionnaire des utilisateurs.
        User recherche = null;
        Connection conn = SimpleDataSource.getConnection();
        try {
            if (!G_Validation.nom(nom) || !G_Validation.courriel(courriel)) {
                return recherche;
            }
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(
                    "SELECT id FROM user WHERE user_name = " /*, racine*/ // à ajouter dans la BD
                    + nom + " AND user_adress = " + courriel);
            rs.next();
            int val = rs.getInt("id");
            int racine = rs.getInt("user_racine");
            if (val < 1 || racine < 1) {
                return null;
            }
            recherche = new User(val, nom, courriel, racine);
        } catch (SQLException e) {
            return null;
        } finally {
            conn.close();
        }
        return recherche;
    }

    /**
     * Setteur de l'identifiant de l'utilisateur
     *
     * @param id L'identifiant
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setteur du nom du compte de l'utilisateur
     *
     * @param nom Le nom du compte
     */
    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setteur du mot de passe de l'utilisateur
     *
     * @param value Le mot de passe
     */
    @Override
    public void setValue(String value) {
        setCourriel(value);
    }

    /**
     * Setteur du mot de passe de l'utilisateur
     *
     * @param mdp Le mot de passe
     */
    public void setCourriel(String mdp) {
        this.courriel = mdp;
    }

    /**
     * Mets à jour la table d'association (TA) des dossiers possédés par
     * l'utilisateur
     *
     * @param ta TA des dossiers possédés par des utilisateurs
     * @param groupbooks Liste des dossiers de l'utilisateur
     */
    public void setGroupbooks(TA_User_GB ta, ArrayList<Groupbook> groupbooks) {
        ArrayList<Groupbook> gbs = Recherche.getRights(this, ta, groupbooks);
        for (Groupbook gb : gbs) {
            // Si des IDs de gb sont déjà présent dans la TA, est-ce qu'il est 
            // préférable de mettre à jour la valeur des gbs dans TA ou de les 
            // effacer pour ensuite les remette dedans la TA?
        }
    }

  /**
   *
   * @param groupbookRacine
   * @throws IOException
   */
  public void setGroupbooks(int groupbookRacine) throws IOException {
        if (groupbookRacine < 1) {
            throw new IOException();
        }
        this.groupbookRacine = groupbookRacine;
        // implémenter d'autres fonctionnalités?
    }

    /**
     * Obtien l'identifiant de l'utilisateur
     *
     * @return L'identifiant
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Obtien de nom du compte de l'utilisateur
     *
     * @return Le nom du compte
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Obtien le mot de passe de l'utilisateur
     *
     * @return Le mot de passe
     */
    @Override
    public String getValue() {
        return getCourriel();
    }

    /**
     * Obtien le mot de passe de l'utilisateur
     *
     * @return Le mot de passe
     */
    public String getCourriel() {
        return courriel;
    }

    /**
     * Vérifie si la chaine saisie est le mot de passe de l'utilisateur dans la
     * base de donnée
     *
     * @param mdp Mot de passe saisie dans l'application
     * @return Vrai si la chaine est identique au mot de passe
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public boolean isMdp(String mdp) throws SQLException, IOException {
        return G_Validation.compareColString("mdp", "id", id, mdp);
    }

    /**
     * Recherche tout les dossiers possédés par l'utilisateur en fonction de la
     * table d'association (TA)
     *
     * @param ta TA des dossiers possédés par les utilisateurs
     * @param groupbooks Dossiers des utilisateurs de l'application
     * @return Liste des dossiers de l'utilisateur
     */
    public ArrayList<Groupbook> getGroupbooks(TA_User_GB ta, ArrayList<Groupbook> groupbooks) {
        return Recherche.getRights(this, ta, groupbooks);
    }

    /**
     * Recherche tout les dossiers possédés par l'utilisateur en fonction de la
     * table d'association (TA)
     *
     * @return Liste des dossiers de l'utilisateur
     *//*
    public ArrayList<Groupbook> getOwnedGroupbooks()
            throws IOException, SQLException, Exception {
        return Gestionnaire.getUserGroupbooks(this);

        /*ArrayList<Groupbook> gbs = getGroupbooks(ta, groupbooks);
    ArrayList<Groupbook> childs = new ArrayList<>();
    gbs.forEach((gb) -> {
      // utiliser addAll() au lieu d'une autre boucle for?
      gb.getGroupbooks().forEach((child) -> {
        childs.add(child);
      });
    });
    gbs.addAll(childs);
    return gbs;
        
    }*/

    public ArrayList<Groupbook> getTheirGroupbooks() {
        return null;
    }

    /**
     * Effectue une connexion de l'utilisateur avec la base de donnée (BD) de
     * l'application
     */
    public void connect() {

    }

    /**
     * Ferme une connexion de l'utilisateur avec la BD de l'application
     */
    public void disconnect() {

    }

  /**
   *
   * @param nomUtilisateur
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public static int getUserId(String nomUtilisateur)
            throws SQLException, IOException {
        // Metre cette méthode dans le gestionnaire des utilisateurs
        if (!G_Validation.nom(nomUtilisateur)) {
            throw new IOException("Le nom de l'utilisateur n'est pas valide pour l'application");
        }
        int val = -1;
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "SELECT id FROM user WHERE user_name = ?");
            stat.setString(1, nomUtilisateur);

            ResultSet rs = stat.executeQuery();
            if (rs.next()) {

                val = rs.getInt(1);
            }

        } finally {
            conn.close();
        }
        return val;
    }
}
