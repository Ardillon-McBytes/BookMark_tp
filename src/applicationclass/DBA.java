/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe parente à toutes les associations utilisés dans les tables
 * d'associations (TAs)
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 * @param <L> Classe du premier champ de la TA de la BD
 * @param <R> Classe du second champ de la TA de la BD
 */
public class DBA<L extends DBField, R extends DBField> {

  /**
   * Identifiant de l'association
   */
  protected int id;

  /**
   * Identifiant de l'élément du premier champ de la TA de la BD
   */
  protected int left;

  /**
   * Identifiant de l'élément du deuxième champ de la TA de la BD
   */
  protected int right;

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   */
  protected final void constructor(int id, int left, int right) {
    this.id = id;
    this.left = left;
    this.right = right;
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Objet du premier élément
   * @param right Objet du deuxième élément
   */
  protected final void constructor(int id, L left, R right) {
    this.id = id;
    setLeft(left);
    setRight(right);
  }

  /**
   * Constructeur
   */
  public DBA() {
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Identifiant du premier élément
   * @param right Identifiant du deuxième élément
   */
  public DBA(int id, int left, int right) {
    constructor(id, left, right);
  }

  /**
   * Constructeur
   *
   * @param id Identifiant de l'association
   * @param left Objet du premier élément
   * @param right Objet du deuxième élément
   */
  public DBA(int id, L left, R right) {
    constructor(id, left, right);
  }

  /**
   * Modifie l'identifiant de l'association
   *
   * @param id Nouvel identifiant
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Modifie l'identifiant du premier élément dans la BD
   *
   * @param left Identifiant du premier élément
   */
  public void setLeft(int left) {
    this.left = left;
  }

  /**
   * Modifie l'identifiant du premier élément dans la BD avec l'objet
   *
   * @param left Objet du premier élément
   */
  public void setLeft(L left) {
    this.left = left.getId();
  }

  /**
   * Modifie l'identifiant du deuxième élément dans la BD
   *
   * @param right Identifiant du deuxième élément
   */
  public void setRight(int right) {
    this.right = right;
  }

  /**
   * Modifie l'identifiant du deuxième élément dans la BD
   *
   * @param right Identifiant du deuxième élément
   */
  public void setRight(R right) {
    this.right = right.getId();
  }

  /**
   * Modifie les identifiants à l'aide d'un couple (ne change pas l'identifiant
   * de l'association)
   *
   * @param pair Couple d'entier identifiant les objets associés
   */
  public void setPair(Pair<Integer> pair) {
    setLeft(pair.id);
    setRight(pair.value);
  }

  /**
   * Obtient l'élément qui possède l'identifiant dans une liste
   *
   * @param <FK> Type de l'élément recherché
   * @param side Identifiant de l'élément recherché
   * @param list Liste des éléments
   * @return L'élément trouvé sinon la valeur null
   */
  public <FK extends DBField> FK DBTA(int side, ArrayList<FK> list) {
    for (FK fk : list) {
      if (fk.getId() == side) {
        return fk;
      }
    }
    return null;
  }

  /**
   * Obtient le premier élément (coté gauche) présent dans une liste
   *
   * @param list Liste des éléments de la première colonne dans la BD
   * @return L'élément trouvé ou la valeur null
   */
  public L getLeft(ArrayList<L> list) {
    // Essai de la méthode générique
    return DBTA(this.left, list);
  }

  /**
   * Obtient l'identifiant du premier élément associé
   *
   * @return L'identifiant de l'élément
   */
  public int getLeft() {
    return this.left;
  }

  /**
   * Obtient le deuxième élément associé (coté droit) présent dans une liste
   *
   * @param list Liste des éléments de la deuxième colonne dans la BD
   * @return L'élément trouvé ou la valeur null
   */
  public R getRight(ArrayList<R> list) {
    // Utilise la méthode générique ici au lieu si elle fonctionne
    for (R r : list) {
      if (r.getId() == this.right) {
        return r;
      }
    }
    return null;
  }

  /**
   * Obtient l'identifiant de l'élément de droite
   *
   * @return L'identifiant
   */
  public int getRight() {
    return this.right;
  }

  /**
   * Obtient la pair d'association
   *
   * @return La pair associée
   */
  public Pair<Integer> getPair() {
    return new Pair(left, right);
  }

  /**
   * Méthode utilisé lors de la comparaison entre chaque DBA
   *
   * @param obj DBA à comparer
   * @return Vrai si les valeurs des deux DBA sont identiques
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
    return !(id != other.id || left != other.left || right != other.right);
  }

  /**
   * Méthode utilisé dans les fonctions de recherches en générant une valeur
   * hash
   *
   * @return Valeur hash
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + this.left;
    hash = 29 * hash + this.right;
    return hash;
  }

}
