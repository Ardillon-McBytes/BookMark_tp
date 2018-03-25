/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

/**
 * Classe d'association entre les marquepage et les tags (Étiquetage des liens)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_BM_Tag extends TABase<Bookmark, Tag> {

  public TA_BM_Tag() {
    TABase.constructor("bookmark_tag",
            1, "id",
            2, "id_bookmark",
            3, "id_tag");
  }
}
