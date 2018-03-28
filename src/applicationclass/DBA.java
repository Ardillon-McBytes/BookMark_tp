/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe parente à toutes les tables d'associations (TAs)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 */
public class DBA<L extends DBField, R extends DBField> {

  /**
   *
   */
  protected int id;

  /**
   *
   */
  protected int left;

  /**
   *
   */
  protected int right;

  /**
   *
   */
  public static void main(String[] args) {

  }

  /**
   *
   * @param id
   * @param left
   * @param right
   */
  protected final void constructor(int id, int left, int right) {
    this.id = id;
    this.left = left;
    this.right = right;
  }

  /**
   *
   * @param id
   * @param left
   * @param right
   */
  protected final void constructor(int id, L left, R right) {
    this.id = id;
    setLeft(left);
    setRight(right);
  }

  public DBA() {
  }

  /**
   *
   * @param id
   * @param left
   * @param right
   */
  public DBA(int id, int left, int right) {
    constructor(id, left, right);
  }

  /**
   *
   * @param id
   * @param left
   * @param right
   */
  public DBA(int id, L left, R right) {
    constructor(id, left, right);
  }

  /**
   *
   * @param id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @param left
   */
  public void setLeft(int left) {
    this.left = left;
  }

  /**
   *
   * @param left
   */
  public void setLeft(L left) {
    this.left = left.getId();
  }

  /**
   *
   * @param right
   */
  public void setRight(int right) {
    this.right = right;
  }

  /**
   *
   * @param right
   */
  public void setRight(R right) {
    this.right = right.getId();
  }

  /**
   *
   * @param pair
   */
  public void setPair(Pair<Integer> pair) {
    setLeft(pair.id);
    setRight(pair.value);
  }

  // Façon qui utilise une méthode générique, mais fonctionne-t-elle?
  public <FK extends DBField> FK DBTA(int side, ArrayList<FK> list) {
    for (FK fk : list) {
      if (fk.getId() == side) {
        return fk;
      }
    }
    return null;
  }

  /**
   *
   * @param llist
   * @return
   */
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

  /**
   *
   * @return
   */
  public int getLeft() {
    return this.left;
  }

  /**
   *
   * @param rlist
   * @return
   */
  public R getRight(ArrayList<R> rlist) {
    // Utilise la méthode générique ici au lieu si elle fonctionne
    for (R r : rlist) {
      if (r.getId() == this.right) {
        return r;
      }
    }
    return null;
  }

  /**
   *
   * @return
   */
  public int getRight() {
    return this.right;
  }

  public Pair<Integer> getPair() {
    return new Pair(left, right);
  }

  /**
   *
   * @param obj
   * @return
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DBA other = (DBA) obj;
    return !(left != other.left || right != other.right);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + this.left;
    hash = 29 * hash + this.right;
    return hash;
  }

}
