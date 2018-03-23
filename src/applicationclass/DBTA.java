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
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 * @throws 
 */
public class DBTA<L extends DBField, R extends DBField> {

  /**
   * 
   */
  protected Integer left;

  /**
   *
   */
  protected Integer right;
  
  /**
   * 
   * @param left
   * @param right 
   */
  protected DBTA(int left, int right) {
    this.left = left;
    this.right = right;
  }
  /**
   * 
   * @param left
   * @param right 
   */
  protected DBTA(L left, R right) {
    this.left = left.getId();
    this.right = right.getId();
  }
  /**
   * 
   * @param left 
   */
  protected void setLeft(int left) {
    this.left = left;
  }
  /**
   * 
   * @param left 
   */
  protected void setLeft(L left){
    this.left = left.getId();
  }
  /**
   * 
   * @param right 
   */
  protected void setRight(int right) {
    this.right = right;
  }
  /**
   * 
   * @param right 
   */
  protected void setRight(R right) {
    this.right = right.getId();
  }
  
  // Façon qui utilise une méthode générique, mais fonctionne-t-elle?
  protected <FK extends DBField> FK DBTA(int side, ArrayList<FK> list) {
    // Utiliser une recherche dicotomique selon 
    // le sens du tri effectif sinon une recherche linéaire?
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
  protected L getLeft(ArrayList<L> llist) {
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
  protected int getLeft() {
    return this.left;
  }
  
  /**
   *
   * @param rlist
   * @return
   */
  protected R getRight(ArrayList<R> rlist) {
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
  protected int getRight() {
    return this.right;
  }
  
   
}
