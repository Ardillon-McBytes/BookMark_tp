/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe parente aux tables d'associations (TAs) qui possèdent un atribut en
 * chaine de caractère
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class DBAI<L extends DBField, R extends DBField> extends DBA {

  /**
   *
   */
  protected int valeur;

  /**
   *
   *
   * @param valeur
   */
  protected final void constructor(int valeur) {
    this.valeur = valeur;
  }

  /**
   *
   *
   * @param id
   * @param left
   * @param right
   * @param info
   */
  public DBAI(int id, int left, int right, int valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  /**
   *
   *
   * @param id
   * @param left
   * @param right
   * @param info
   */
  public DBAI(int id, L left, R right, int valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  public void setValeur(int valeur) {
    this.valeur = valeur;
  }

  public int getValeur() {
    return valeur;
  }
}
