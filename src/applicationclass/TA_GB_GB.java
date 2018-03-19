/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * 
 * @author olivi
 */
public class TA_GB_GB extends ArrayList<DBTA<Groupbook, Groupbook>> {
  
  /**
   * 
   * @param id
   * @param gb
   * @return
   */
  public boolean setParentGroupbook(int id, Groupbook gb) {
    for (DBTA<Groupbook, Groupbook> ta : this) {
      // Ou l'inverse? ... if (bm.getId() == ta.getLeft()) {ta.setLeft(id); ...}
      if (id == ta.getLeft()) {
        ta.setLeft(gb);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param id
   * @param gb
   * @return
   */
  public boolean setChildGroupbook(int id, Groupbook gb) {
    for (DBTA<Groupbook, Groupbook> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(gb);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param child
   * @param gbs
   * @return
   */
  // Ne prendre que le premier ou toute la liste?
  public ArrayList<Groupbook> getParentGroupbooks(
          Groupbook child, ArrayList<Groupbook> gbs) {
    return Recherche.getParentGroupbooks(child, this, gbs)/*.get(0)*/;
  }

  /**
   *
   * @param parent
   * @param gbs
   * @return
   */
  public /*ArrayList<*/Groupbook/*>*/ getChildGroupbooks(
          Groupbook parent, ArrayList<Groupbook> gbs) {
    return Recherche.getChildGroupbooks(parent, this, gbs).get(0);
  }
}
