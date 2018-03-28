/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Classe des associations utilisés dans les TAs qui possèdent un atribut en
 * chaine de caractère
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 */
public class DBAS<L extends DBField, R extends DBField> extends DBA {

  /**
   * Chaine de caractère assignée à la paire
   */
  protected String info;

  /**
   * Constructeur
   *
   * @param info Chaine de caractère assignée à la paire
   */
  protected final void constructor(String info) {
    this.info = info;
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   * @param info Chaine de caractère assignée à la paire
   */
  public DBAS(int id, int left, int right, String info) {
    super.constructor(id, left, right);
    constructor(info);
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   * @param info Chaine de caractère assignée à la paire
   */
  public DBAS(int id, L left, R right, String info) {
    super.constructor(id, left, right);
    constructor(info);
  }

  /**
   * Modifie la chaine de caractère assignée à la paire
   *
   * @param info Nouvelle chaine de caractère assignée à la paire
   */
  public void setInfo(String info) {
    this.info = info;
  }

  /**
   * Obtient la chaine de caractère assignée à la paire
   *
   * @return La chaine de caractère
   */
  public String getInfo() {
    return info;
  }
}
