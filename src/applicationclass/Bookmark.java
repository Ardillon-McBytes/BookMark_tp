/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

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
   *
   * @param id
   * @param nom
   * @param url
   * @param description
   */
  
  public Bookmark()
  {}
  public Bookmark(int id, String nom, String url, String description) {
    this.id = id;
    this.nom = nom;
    this.url = url;
    this.description = description;
  }
  
  public Bookmark(String nom, String url, String description) {
    this.id = id;
    this.nom = nom;
    this.url = url;
    this.description = description;
  }
  
  /**
   *
   * @param id
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   *
   * @param nom
   */
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }
  
  /**
   *
   * @param value
   */
  @Override
  public void setValue(String value) {
    setUrl(value);
  }

  /**
   *
   * @param url
   */
  public void setUrl(String url) {
    this.url = url;
  }
  
  /**
   *
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   *
   * @return
   */
  @Override
  public int getId() {
    return id;
  }
  
  /**
   *
   * @return
   */
  @Override
  public String getNom() {
    return nom;
  }
  
  /**
   *
   * @return
   */
  @Override
  public String getValue() {
    return getUrl();
  }

  /**
   *
   * @return
   */
  public String getUrl() {
    return url;
  }
  
  /**
   *
   * @return
   */
  public String getDescription() {
    return description;
  }
  
  /**
   *
   * @param tagged
   * @param tags
   * @return
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
   *
   * @param folders
   * @param groupbooks
   * @return
   */
  public ArrayList<Groupbook> getGroupbooks(TA_GB_BM folders, ArrayList<Groupbook> groupbooks) {
    return Recherche.getGroupbooks(this, folders, groupbooks);
  }
  
}
