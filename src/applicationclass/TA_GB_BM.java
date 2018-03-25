/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe d'association entre les marquepages et les dossiers de marquepage
 * (Arborescence)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_GB_BM extends TABase<Groupbook, Bookmark> {

  public TA_GB_BM() {
    TABase.constructor("bookmark_group",
            1, "id",
            3, "id_bookmark",
            2, "id_group");
  }

}
