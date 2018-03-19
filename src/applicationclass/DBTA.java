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
 * @param <L>
 * @param <R>
 */
public class DBTA<L extends DBField, R extends DBField> {
  protected int left;
  protected int right;
  
  public DBTA(int left, int right) {
    this.left = left;
    this.right = right;
  }
  public DBTA(L left, R right) {
    this.left = left.getId();
    this.right = right.getId();
  }
  public void setLeft(int left) {
    this.left = left;
  }
  public void setLeft(L left){
    this.left = left.getId();
  }
  public void setRight(int right) {
    this.right = right;
  }
  public void setRight(R right) {
    this.right = right.getId();
  }
  
  // Façon qui utilise une méthode générique, mais fonctionne-t-elle?
  private <FK extends DBField> FK DBTA(int side, ArrayList<FK> list) {
    for (FK fk : list) {
      if (fk.getId() == side) {
        return fk;
      }
    }
    return null;
  }
  
  public L getLeft(ArrayList<L> llist) {
    // Essai de la méthode générique
    return DBTA(this.left, llist);
    /*
    // Autre manière de faire la méthode
    for (L l : llist) {
      if (l.getId() == this.left) {
        return l;
      }
    }
    return null;
    */
  }
  public int getLeft() {
    return this.left;
  }
  
  public R getRight(ArrayList<R> rlist) {
    // Utilise la méthode générique ici au lieu si elle fonctionne
    for (R r : rlist) {
      if (r.getId() == this.right) {
        return r;
      }
    }
    return null;
  }
  public int getRight() {
    return this.right;
  }
}
