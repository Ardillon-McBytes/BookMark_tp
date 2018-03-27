/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe parente aux tables d'associations (TAs) qui possèdent un atribut en chaine de caractère
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class DBAS<L extends DBField, R extends DBField> extends DBA {
  
  /**
   * 
   */
  protected String info;
  
  /**
   * 
   * 
   * @param valeur
   * @param info
   */
  protected final void constructor(String info) {
    this.info = info;
  }
  
  /**
   * 
   * 
   * @param id
   * @param left
   * @param right
   * @param info
   */
  public DBAS(int id, int left, int right, String info) {
    super.constructor(id, left, right);
    constructor(info);
  }
  
  /**
   * 
   * 
   * @param id
   * @param left
   * @param right
   * @param info
   */
  public DBAS(int id, L left, R right, String info) {
    super.constructor(id, left, right);
    constructor(info);
  }
  
  public void setInfo(String info) {
    this.info = info;
  }
  
  public String getInfo() {
    return info;
  }
}
