/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe pour les marquepages
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Bookmark implements DBField {
  private int id;
  private String nom;
  private String url;
  private String description;

  public Bookmark(int id, String nom, String url, String description) {
    this.id = id;
    this.nom = nom;
    this.url = url;
    this.description = description;
  }
  
  @Override
  public void setId(int id) {
    this.id = id;
  }
  
  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }
  
  @Override
  public void setValue(String value) {
    setUrl(value);
  }
  public void setUrl(String url) {
    this.url = url;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public int getId() {
    return id;
  }
  
  @Override
  public String getNom() {
    return nom;
  }
  
  @Override
  public String getValue() {
    return getUrl();
  }
  public String getUrl() {
    return url;
  }
  
  public String getDescription() {
    return description;
  }
  
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
  
  public ArrayList<Groupbook> getGroupbooks(TA_GB_BM folders, ArrayList<Groupbook> groupbooks) {
    return Recherche.getGroupbooks(this, folders, groupbooks);
  }
  
}
