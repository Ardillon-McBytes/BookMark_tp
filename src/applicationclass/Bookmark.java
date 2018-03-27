/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe pour les marquepages
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Bookmark implements DBField {

  private int id;
  private String nom;
  private String url;
  private String description;

  /**
   * Constructeur
   * 
   * @param id
   * @param nom
   * @param description
   */
  public Bookmark() {
  }

  /**
   * Constructeur
   * 
   * @param id
   * @param nom
   * @param url
   * @param description
   */
  public Bookmark(int id, String nom, String url, String description) {
    this.id = id;
    this.nom = nom;
    this.url = url;
    this.description = description;
  }

  /**
   * Constructeur
   * 
   * @param nom
   * @param url
   * @param description
   */
  public Bookmark(String nom, String url, String description) {
    this.id = id;
    this.nom = nom;
    this.url = url;
    this.description = description;
  }

  /**
   * Constructeur
   * 
   * @param rs
   * @throws SQLException
   */
  public Bookmark(ResultSet rs) throws SQLException {
    this.id = rs.getInt("id");
    this.nom = rs.getString("nom");
    this.url = rs.getString("url");
    this.description = rs.getString("description");
  }

  /**
   * Change l'identifiant du bookmark
   *
   * @param id Nouveau identifiant
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Change le nom du bookmark
   *
   * @param nom Nouveau nom
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  /**
   * Change l'url du bookmark
   *
   * @param value Nouveau url
   */
  @Override
  public void setValue(String value) {
    setUrl(value);
  }

  /**
   * Change l'url du bookmark
   *
   * @param url Nouveau url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Change la description du bookmark
   *
   * @param description Nouvelle description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Obtient l'identifiant du bookmark
   *
   * @return L'identifiant
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Obtient le nom du bookmark
   *
   * @return Le nom
   */
  @Override
  public String getNom() {
    return nom;
  }

  /**
   * Obtient l'url du bookmark
   *
   * @return L'url
   */
  @Override
  public String getValue() {
    return getUrl();
  }

  /**
   * Obtient l'url du bookmark
   *
   * @return L'url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Obtient la description du bookmark
   *
   * @return La description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Obtient la liste des tags associée au bookmark
   *
   * @param tagged TA des étiquetages
   * @param tags Liste des tags
   * @return Liste des tags associée
   */
  public ArrayList<Tag> getTags(TA_BM_Tag tagged, ArrayList<Tag> tags) {
    return Recherche.getTags(this, tagged, tags);

    /*
    ArrayList<Tag> result = new ArrayList<>();
    for (TA_BM_Tag ta: tagged) {
      if (id == ta.getBookmark()) {
        
        tags.stream().filter((tag) -> (tag.getId() 
              == ta.getTag())).forEachOrdered((tag) -> {
          result.add(tag);
        });
        break;
      }
    }
    return result;
     */
  }

  /**
   * Obtient la liste des groupbooks associée u bookmark
   *
   * @param folders TA des contenues
   * @param groupbooks Liste des groupbooks
   * @return Liste des groupbooks associée
   */
  public ArrayList<Groupbook> getGroupbooks(TA_GB_BM folders, ArrayList<Groupbook> groupbooks) {
    return Recherche.getGroupbooks(this, folders, groupbooks);
  }

}
