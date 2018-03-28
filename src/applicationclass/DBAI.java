/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Classe des associations utilisés dans les TAs qui possèdent un atribut en
 * entier
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 */
public class DBAI<L extends DBField, R extends DBField> extends DBA {

  /**
   * Valeur assignée à la paire
   */
  protected int valeur;

  /**
   * Modifie la valeur assignée à la paire
   *
   * @param valeur Nouvelle valeur de la paire
   */
  protected final void constructor(int valeur) {
    this.valeur = valeur;
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   * @param valeur Valeur assignée à la paire
   */
  public DBAI(int id, int left, int right, int valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   * @param valeur Valeur assignée à la paire
   */
  public DBAI(int id, L left, R right, int valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  /**
   * Modifie la valeur assignée à la paire
   *
   * @param valeur Nouvelle valeur assignée à la paire
   */
  public void setValeur(int valeur) {
    this.valeur = valeur;
  }

  /**
   * Obtient la valeur assignée à la paire
   *
   * @return Valeur assignée à la paire
   */
  public int getValeur() {
    return valeur;
  }
}
