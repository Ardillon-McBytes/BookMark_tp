/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe pour les tags
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Tag implements DBField {
  private int id;
  private String nom;
  private String description;

  public Tag(int id, String nom, String description) {
    this.id = id;
    this.nom = nom;
    this.description = description;
  }

  public Tag(int id, String nom) {
    this.id = id;
    this.nom = nom;
    this.description = getCurrentTimeStamp();
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
    setDescription(value);
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
    return getDescription();
  }
  public String getDescription() {
    return description;
  }
  
  private String getCurrentTimeStamp() {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdfDate.format(new Date());
  }
  
  public boolean equals(Tag tag) {
    return id == tag.getId();
  }
  
  public boolean equals(DBTA<Bookmark, Tag> ta) {
    return id == ta.getRight();
  }
  
  public boolean equals(int id) {
    return this.id == id;
  }  
}
