/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Interface pour les classes métiers
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public interface DBField {

  /**
   * Modifie l'identifiant de l'élément
   *
   * @param id Nouvel identifiant de l'élément
   */
  public void setId(int id);

  /**
   * Modifie le nom de l'élément
   *
   * @param nom Nouveau nom de l'élément
   */
  public void setNom(String nom);

  /**
   * Modifie la valeur de l'élément
   *
   * @param value Nouvelle valeur de l'élément
   */
  public void setValue(String value);

  /**
   * Obtient l'identifiant de l'élément
   *
   * @return L'identifiant
   */
  public int getId();

  /**
   * Obtient le nom de l'élément
   *
   * @return Le nom
   */
  public String getNom();

  /**
   * Obtient la valeur de l'élément
   *
   * @return La valeur
   */
  public String getValue();
}
