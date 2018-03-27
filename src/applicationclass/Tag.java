/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe pour les tags
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Tag implements DBField {

  private int id;
  private String nom;
  private String description;

  /**
   *
   * @param id
   * @param nom
   * @param description
   */
  public Tag(int id, String nom, String description) {
    this.id = id;
    this.nom = nom;
    this.description = description;
  }

  /**
   *
   * @param id
   * @param nom
   */
  public Tag(int id, String nom) {
    this.id = id;
    this.nom = nom;
    this.description = getCurrentTimeStamp();
  }
  
  /**
   * 
   * @param rs
   * @throws java.sql.SQLException
   */
  public Tag(ResultSet rs) throws SQLException {
    this.id = rs.getInt("id");
    this.nom = rs.getString("nom");
    this.description = rs.getString("description");
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
    setDescription(value);
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
    return getDescription();
  }

  /**
   *
   * @return
   */
  public String getDescription() {
    return description;
  }

  private String getCurrentTimeStamp() {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdfDate.format(new Date());
  }

  /**
   *
   * @param tag
   * @return
   */
  public boolean equals(Tag tag) {
    return id == tag.getId();
  }

  /**
   *
   * @param ta
   * @return
   */
  public boolean equals(DBTA<Bookmark, Tag> ta) {
    return id == ta.getRight();
  }

  /**
   *
   * @param id
   * @return
   */
  public boolean equals(int id) {
    return this.id == id;
  }
}
