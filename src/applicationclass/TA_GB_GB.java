/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 *
 * @author olivi
 */
public class TA_GB_GB extends ArrayList<DBTA<Groupbook, Groupbook>> {
  
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

  public boolean setChildGroupbook(int id, Groupbook gb) {
    for (DBTA<Groupbook, Groupbook> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(gb);
        return true;
      }
    }
    return false;
  }

  // Ne prendre que le premier ou toute la liste?
  public ArrayList<Groupbook> getParentGroupbooks(
          Groupbook child, ArrayList<Groupbook> gbs) {
    return Recherche.getParentGroupbooks(child, this, gbs)/*.get(0)*/;
  }

  public /*ArrayList<*/Groupbook/*>*/ getChildGroupbooks(
          Groupbook parent, ArrayList<Groupbook> gbs) {
    return Recherche.getChildGroupbooks(parent, this, gbs).get(0);
  }
}
