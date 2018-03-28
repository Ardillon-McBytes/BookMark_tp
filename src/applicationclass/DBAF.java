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
public class DBAF<L extends DBField, R extends DBField> extends DBA {

  /**
   *
   */
  protected float valeur;

  /**
   *
   *
   * @param valeur
   */
  protected final void constructor(float valeur) {
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
  public DBAF(int id, int left, int right, float valeur) {
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
  public DBAF(int id, L left, R right, float valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  public void setValeur(float valeur) {
    this.valeur = valeur;
  }

  public float getValeur() {
    return valeur;
  }
}
