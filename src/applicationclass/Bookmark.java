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
public class Bookmark {
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

  public void setId(int id) {
    this.id = id;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }
  
  public ArrayList<Tag> getTags(ArrayList<TA_BM_Tag> tags) {
    ArrayList<Tag> result = new ArrayList<>();
    for (TA_BM_Tag ta: tags) {
      //result.add(ta);
    }
    return result;
  }
}
