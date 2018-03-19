/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe pour les utilisateurs
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class User implements DBField {
  private int id;
  private String nom;
  private String mdp;

  /**
   * Constructeur de la classe des utilisateurs
   * @param id Identifiant
   * @param nom Nom du compte
   * @param mdp Mot de passe
   * @param groupBooks Liste des dossiers que l'utilisateur possède
   * @param ta Table d'association (TA) où l'on retrouve les dossiers des utilisateurs
   */
  public User(int id, String nom, String mdp, 
          ArrayList<Groupbook> groupBooks, TA_User_GB ta) {
    this.id = id;
    this.nom = nom;
    this.mdp = mdp;
    setGroupBooks(ta, groupBooks);
  }

  /**
   * Setteur de l'identifiant de l'utilisateur
   * @param id L'identifiant
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Setteur du nom du compte de l'utilisateur
   * @param nom Le nom du compte
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Setteur du mot de passe de l'utilisateur
   * @param value Le mot de passe
   */
  @Override
  public void setValue(String value) {
    setMdp(value);
  }
  /**
   * Setteur du mot de passe de l'utilisateur
   * @param mdp Le mot de passe
   */
  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  /**
   * Mets à jour la table d'association (TA) des dossiers possédés par l'utilisateur
   * @param ta TA des dossiers possédés par des utilisateurs
   * @param groupbooks Liste des dossiers de l'utilisateur
   */
  public void setGroupBooks(TA_User_GB ta, ArrayList<Groupbook> groupbooks) {
    ArrayList<Groupbook> gbs = Recherche.getGroupbooks(this, ta, groupbooks);
    for (Groupbook gb : gbs) {
      // Si des IDs de gb sont déjà présent dans la TA, est-ce qu'il est 
      // préférable de mettre à jour la valeur des gbs dans TA ou de les 
      // effacer pour ensuite les remette dedans la TA?
    }
  }

  /**
   * Obtien l'identifiant de l'utilisateur
   * @return L'identifiant
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Obtien de nom du compte de l'utilisateur
   * @return Le nom du compte
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Obtien le mot de passe de l'utilisateur
   * @return Le mot de passe
   */
  @Override
  public String getValue() {
    return getMdp();
  }
  /**
   * Obtien le mot de passe de l'utilisateur
   * @return Le mot de passe
   */
  protected String getMdp() {
    return mdp;
  }
  
  /**
   * Vérifie si la chaine saisie est le mot de passe de l'utilisateur
   * @param mdp Mot de passe saisie dans l'application
   * @return Vrai si la chaine est identique au mot de passe
   */
  public boolean isMdp(String mdp) {
    return (this.mdp == null ? mdp == null : this.mdp.equals(mdp));
  }

  /**
   * Recherche tout les dossiers possédés par l'utilisateur en fonction de la table d'association (TA)
   * @param ta TA des dossiers possédés par les utilisateurs
   * @param groupbooks Dossiers des utilisateurs de l'application
   * @return Liste des dossiers de l'utilisateur
   */
  public ArrayList<Groupbook> getGroupbooks(TA_User_GB ta, ArrayList<Groupbook> groupbooks) {
    return Recherche.getGroupbooks(this, ta, groupbooks);
  }
  
  /**
   * Recherche tout les dossiers possédés par l'utilisateur en fonction de la table d'association (TA)
   * @param ta TA des dossiers possédés par les utilisateurs
   * @param groupbooks Dossiers des utilisateurs de l'application
   * @return Liste des dossiers de l'utilisateur
   */
  public ArrayList<Groupbook> getAllGroupbooks(TA_User_GB ta, ArrayList<Groupbook> groupbooks) {
    ArrayList<Groupbook> gbs = getGroupbooks(ta, groupbooks);
    ArrayList<Groupbook> childs = new ArrayList<>();
    gbs.forEach((gb) -> {
      // utiliser addAll() au lieu d'une autre boucle for?
      gb.getGroupBooks().forEach((child) -> {
        childs.add(child);
      });
    });
    gbs.addAll(childs);
    return gbs;
  }
  
  /**
   * Effectue une connexion de l'utilisateur avec la base de donnée (BD) de l'application
   */
  public void connect() {
    
  }
  
  /**
   * Ferme une connexion de l'utilisateur avec la BD de l'application
   */
  public void disconnect() {
    
  }
  
}
