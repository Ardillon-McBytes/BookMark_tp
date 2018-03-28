/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Classe des associations utilisés dans les TAs qui possèdent un atribut en
 * float
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 */
public class DBAF<L extends DBField, R extends DBField> extends DBA {

  /**
   * Valeur associé aux identifiants
   */
  protected float valeur;

  /**
   * Constructeur
   *
   * @param valeur Valeur de la pair
   */
  protected final void constructor(float valeur) {
    this.valeur = valeur;
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   * @param valeur Valeur de pair avec l'association
   */
  public DBAF(int id, int left, int right, float valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Objet du premier élément
   * @param right Objet du deuxième élément
   * @param valeur Valeur de pair avec l'association
   */
  public DBAF(int id, L left, R right, float valeur) {
    super.constructor(id, left, right);
    constructor(valeur);
  }

  /**
   * Modifie la valeur de la pair
   *
   * @param valeur Nouvelle valeur
   */
  public void setValeur(float valeur) {
    this.valeur = valeur;
  }

  /**
   * Obtient la valeur de la pair
   *
   * @return Valeur de la pair
   */
  public float getValeur() {
    return valeur;
  }
}
