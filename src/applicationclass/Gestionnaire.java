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
import java.util.HashSet;
import java.util.Set;
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
    
    private static G_Validation gValid;
    private static G_User gUser;
    private static User usagerActif;   // Compte actif?

    private static G_TA gTA;
    private static G_Partage gPartage;
    private static TA_User_GB acces;
    private static G_GB gGB;
    private static TA_GB_GB conteneurs;
    private static G_BM gBM;
    private static TA_GB_BM contenus;
    private static G_Tag gTag;
    private static TA_BM_Tag etiquettes;
    
    private static Recherche r;
    private static G_Requete gRequete;
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
    public static boolean initNouveau(String nomUtilisateur, String courriel, String mdp)
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
    
    public static void loadUserGb() throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        groupbooks.clear();
        groupbooks.add(new Groupbook());
      
        
        PreparedStatement stat = conn.prepareStatement(
                "(SELECT id_groupBook FROM user_group WHERE id_user = '" + getUsagerActif().getId() + "')");
        
        ResultSet rs = stat.executeQuery();
        int id_gp =getUsagerActif().getId();
        
        
        Bookmark bm = new Bookmark();
        
        while (rs.next()) {
            id_gp = rs.getInt(1);
            groupbooks.get(groupbooks.size()-1).setId(id_gp);
            
            PreparedStatement stat2 = conn.prepareStatement(
                    "(SELECT id_bookmark FROM bookmark_group WHERE id_group = '" + id_gp + "')");
            
            ResultSet rs2 = stat2.executeQuery();
            
            while (rs2.next()) {
                int id_bm = rs.getInt(1);
                bm.setId(id_bm);
                contenus.add(new DBTA<Groupbook, Bookmark>(groupbooks.get(groupbooks.size()-1), bm));
                
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
                    groupbooks.get(groupbooks.size()-1).addBookmark(bm.getId());
                }
            }
            
        }
       
    }
    
    public static boolean initExistant(String nomUtilisateur, String courriel, String mdp)
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
    
    public static boolean chargerUserData() throws Exception {
        if (usagerActif == null || userValidation(usagerActif) == null) {
            //"L'utilisateur n'a pas encore été initialisé."
            return false;
        }
        //setGroupbooks(G_GB.getAllGroupbooks());
        return true;
    }

    /**
     *
     * @param user
     * @return
     */
    public static boolean addUser(User user) throws SQLException, IOException {
        Groupbook gb;
        if (Recherche.containsLeft(acces, user)) {
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
    
    public static void setUsagerActif(User u)
            throws IOException, SQLException {
        usagerActif = userValidation(u);
    }
    
    public static User getUsagerActif() {
        return usagerActif;
    }

    /**
     *
     * @param gb
     */
    public static void addGroupbook(Groupbook gb) {
        groupbooks.add(gb);
    }

    /**
     *
     * @param bm
     */
    public static void addBookmark(Bookmark bm) {
        bookmarks.add(bm);
    }

    /**
     *
     * @param t
     */
    public static void addTag(Tag t) {
        tags.add(t);
    }

    /**
     *
     */
    public static void addId(int i) throws IOException {
        if (i < 1) {
            throw new IOException("Identifiant invalide");
        }
        identifiants.add(i);
    }

    /**
     *
     */
    public static void add() {
        //.add();
    }
    
    public static Groupbook getUserRacineGroupbook(User user)
            throws Exception {
        int id = user.getRacine();
        for (Groupbook gb : Recherche.getRights(user, acces, groupbooks)) {
            if (id == gb.getId()) {
                return gb;
            }
        }
        return null;
    }
    
    public static ArrayList<Groupbook> getUserGroupbooks(User user)
            throws Exception {
        return getChildGroupbooks(getUserRacineGroupbook(user));
    }
    
    public static ArrayList<Groupbook> getChildGroupbooks(Groupbook parent)
            throws IOException, SQLException {
        /*if (null == G_Validation.userValidation(user)) {
    throw new IOException("Utilisateur mal initialisé");
    }*/
        return Recherche.getRights(parent, conteneurs, groupbooks);
    }

    /**
     * GETTEURS ET SETTEURS DE BASE ***************************
     */
    /**
     * Obtien la table d'association (TA) qui lie les utilisateurs à leurs
     *
     * @return
     */
    public static TA_User_GB getAcces() {
        return acces;
    }
    
    public static TA_GB_GB getConteneurs() {
        return conteneurs;
    }
    
    public static TA_GB_BM getContenus() {
        return contenus;
    }
    
    public static TA_BM_Tag getEtiquettes() {
        return etiquettes;
    }
    
    public static ArrayList<User> getUsers() {
        return users;
    }
    
    public static ArrayList<Groupbook> getGroupbooks() {
        return groupbooks;
    }
    
    public static ArrayList<Bookmark> getBookmarks() {
        return bookmarks;
    }
    
    public static ArrayList<Groupbook> getCurrentGroupbooks() throws SQLException, Exception {
        
        return usagerActif.getOwnedGroupbooks();
        
    }
    
    public static ArrayList<Tag> getTags() {
        return tags;
    }
    
    public static ArrayList<Integer> getIdentifiants() {
        return identifiants;
    }
    
    public static void setAcces(TA_User_GB acces) {
        Gestionnaire.acces = acces;
    }
    
    public static void setConteneurs(TA_GB_GB conteneurs) {
        Gestionnaire.conteneurs = conteneurs;
    }
    
    public static void setContenus(TA_GB_BM contenus) {
        Gestionnaire.contenus = contenus;
    }
    
    public static void setEtiquettes(TA_BM_Tag etiquettes) {
        Gestionnaire.etiquettes = etiquettes;
    }
    
    public static void setUsers(ArrayList<User> users) {
        Gestionnaire.users = users;
    }
    
    public static void setGroupbooks(ArrayList<Groupbook> groupbooks) {
        Gestionnaire.groupbooks = groupbooks;
    }
    
    public static void setBookmarks(ArrayList<Bookmark> bookmarks) {
        Gestionnaire.bookmarks = bookmarks;
    }
    
    public static void setTags(ArrayList<Tag> tags) {
        Gestionnaire.tags = tags;
    }
    
    public static void setIdentifiants(ArrayList<Integer> identifiants) {
        Gestionnaire.identifiants = identifiants;
    }
}
