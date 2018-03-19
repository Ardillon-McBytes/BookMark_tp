/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 *
 * @author olivi
 */
public interface DBField {

  /**
   *
   * @param id
   */
  public void setId(int id);

  /**
   *
   * @param nom
   */
  public void setNom(String nom);

  /**
   *
   * @param value
   */
  public void setValue(String value);

  /**
   *
   * @return
   */
  public int getId();

  /**
   *
   * @return
   */
  public String getNom();

  /**
   *
   * @return
   */
  public String getValue();
}
