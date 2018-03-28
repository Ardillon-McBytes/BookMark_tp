/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 *
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <R>
 */
public class Pair<R> {

  /**
   *
   */
  public int id;

  /**
   *
   */
  public R value;

  /**
   *
   * @param id
   * @param nom
   */
  public Pair(int id, R nom) {
    this.id = id;
    this.value = nom;
  }
}
