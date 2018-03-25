/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Classe qui représente la table d'association (TA) entre les Groupbooks
 * conteneurs (parent) et ceux contenus (enfant).
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_GB_GB extends TABase<Groupbook, Groupbook> {

  /**
   * Constructeur de TA_GB_GB identifiant celui de l'objet Groupbook. (remplace
   * un dossier par un autre... est-ce utile?)
   */
  public TA_GB_GB() {
    TABase.constructor("currently undefined",
            1, "id",
            2, "id_parent",
            3, "id_child");
  }
}
