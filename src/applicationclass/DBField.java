/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Interface pour les classes métiers
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
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
