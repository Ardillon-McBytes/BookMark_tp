/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe qui fait la gestion des recherches è partir de tables d'associations
 * (TAs) et du contenu de l BD.
 *
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class Recherche {

  /**
   * Méthode de tests
   *
   * @param args
   */
  public static void main(String[] args) {

  }

  /**
   * Obtien une liste de tout les éléments de droite comprit dans la TA qui ont
   * comme pair l'objet de gauche
   *
   * @param <FK1> Type des éléments de gauche comprit dans la TA
   * @param <FK2> Type des éléments de droite comprit dans la TA
   * @param <A> Le type d'assossiation
   * @param <TA> La TA correspondant aux éléments
   * @param fk1 Élément de gauche où l'on recherche tout ses paires dans la TA
   * @param dbta TA qui lie les éléments de gauche à tout les éléments de droite
   * @param list Liste des objets instanciés qui représente la liste de
   * @return Liste des objets à la droite des paires
   */
  private static <FK2 extends DBField, FK1 extends DBField, A extends DBA<FK1, FK2>, TA extends ArrayList<A>>
          ArrayList<FK2> matchingLeft(FK1 fk1, TA dbta, ArrayList<FK2> list) {

    ArrayList<FK2> result = new ArrayList<>();
    //if (dbta.get(0).getLeft() instanceof FK1) {
    dbta.stream().filter((fk) -> (fk.getRight()
            == fk1.getId())).forEachOrdered((fk) -> {
      result.add((FK2) fk);
    });
    //}

    return result;
  }

  private static <FK1 extends DBField, FK2 extends DBField, A extends DBA<FK1, FK2>, TA extends ArrayList<A>>
          ArrayList<FK1> matchingRight(
                  FK2 fk2, TA dbta, ArrayList<FK1> list) {

    ArrayList<FK1> result = new ArrayList<>();
    dbta.stream().filter((fk) -> (fk.getLeft()
            == fk2.getId())).forEachOrdered((fk) -> {
      result.add((FK1) fk);
    });

    return result;
  }

  /**
   *
   * @param bookmark
   * @param tagList
   * @param tags
   * @return
   */
  public static ArrayList<Tag> getTags(
          Bookmark bookmark, TA_BM_Tag tagList, ArrayList<Tag> tags) {
    return matchingLeft(bookmark, tagList, tags);
  }

  /**
   *
   * @param tag
   * @param tagList
   * @param bookmarks
   * @return
   */
  public static ArrayList<Bookmark> getBookmarks(
          Tag tag, TA_BM_Tag tagList, ArrayList<Bookmark> bookmarks) {
    // Utilisant la méthode générique
    return matchingRight(tag, tagList, bookmarks);
    /*
    ArrayList<Bookmark> result = new ArrayList<>();
    
    for (DBA<Bookmark, Tag> ta : tagged) {
      if (tag.equals(ta.getRight())) {
        
        bms.stream().filter((bm) -> (bm.getId() 
              == ta.getLeft())).forEachOrdered((bm) -> {
          result.add(bm);
        });
        break;
      }
    }
    return result;
     */
  }

  /**
   *
   * @param groupbook
   * @param folderList
   * @param bookmarks
   * @return
   */
  public static ArrayList<Bookmark> getBookmarks(
          Groupbook groupbook, TA_GB_BM folderList, ArrayList<Bookmark> bookmarks) {
    return matchingLeft(groupbook, folderList, bookmarks);
  }

  /**
   *
   * @param bookmark
   * @param folderList
   * @param groupbooks
   * @return
   */
  public static ArrayList<Groupbook> getGroupbooks(
          Bookmark bookmark, TA_GB_BM folderList, ArrayList<Groupbook> groupbooks) {
    return matchingRight(bookmark, folderList, groupbooks);
  }

  /**
   *
   * @param child
   * @param folderList
   * @param groupbooks
   * @return
   */
  public static ArrayList<Groupbook> getParentGroupbooks(
          Groupbook child, TA_GB_GB folderList, ArrayList<Groupbook> groupbooks) {
    return matchingLeft(child, folderList, groupbooks);
  }

  /**
   *
   * @param parent
   * @param folderList
   * @param groupbooks
   * @return
   */
  public static ArrayList<Groupbook> getChildGroupbooks(
          Groupbook parent, TA_GB_GB folderList, ArrayList<Groupbook> groupbooks) {
    return matchingRight(parent, folderList, groupbooks);
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param left
   * @param ta
   * @param rights
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<R> getRights(L left, TA ta, ArrayList<R> rights) {
    return matchingLeft(left, ta, rights);
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param right
   * @param ta
   * @param lefts
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<L> getLefts(R right, TA ta, ArrayList<L> lefts) {
    return matchingRight(right, ta, lefts);
  }

  // Peut-on formuler une méthode générique pour réduire les répéritions?
  // Voir l'exemple avec la liste de TA_BM_Tag et Tag
  // P-e que l'on devrait implémenter cette méthode dans les TA, 
  //    mais il existe aussi p-e une méthode déjà existante à cause du ArrayList
  // L'ordre des parametre change parfois sinon des signatures se trouvent à être en confli.
  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param id
   * @param left
   * @param right
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          boolean contains(TA ta, int id, L left, R right) {
    return ta.contains((A) new DBA(id, left, right));
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param left
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          boolean containsLeft(TA ta, L left) {
    return ta.stream().anyMatch((d) -> (d.left == left.getId()));
  }

  /**
   *
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param left
   * @param rights
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<R> getMatchingLeft(TA ta, L left, ArrayList<R> rights) {
    ArrayList<Integer> pos = getLeftPositions(ta, left);
    ArrayList<R> result = new ArrayList<>();
    pos.forEach((p) -> {
      result.add(rights.get(p));
    });
    return result;
  }

  /**
   *
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param right
   * @param lefts
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<L> getMatchingRight(TA ta, R right, ArrayList<L> lefts) {
    ArrayList<Integer> pos = getRightPositions(ta, right);
    ArrayList<L> result = new ArrayList<>();
    pos.forEach((p) -> {
      result.add(lefts.get(p));
    });
    return result;
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param right
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          boolean containsRight(TA ta, R right) {
    return ta.stream().anyMatch((d) -> (d.right == right.getId()));
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param id
   * @param left
   * @param right
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          int getPosition(TA ta, int id, L left, R right) {
    int pos = 0;
    for (A t : ta) {
      if (t.equals(new DBA(id, left, right))) {
        return pos;
      }
      pos++;
    }
    return pos; // ou -1
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param left
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<Integer> getLeftPositions(TA ta, L left) {
    ArrayList<Integer> places = new ArrayList<>();
    int pos = 0;
    for (DBA<L, R> t : ta) {
      if (t.getLeft() == left.getId()) {
        places.add(pos);
      }
      pos++;
    }
    return places;
  }

  /**
   *
   * @param <L>
   * @param <R>
   * @param <A>
   * @param <TA>
   * @param ta
   * @param right
   * @return
   */
  public static <L extends DBField, R extends DBField, A extends DBA<L, R>, TA extends TABase<L, R, A>>
          ArrayList<Integer> getRightPositions(TA ta, R right) {
    ArrayList<Integer> places = new ArrayList<>();
    int pos = 0;
    for (DBA<L, R> t : ta) {
      if (t.getRight() == right.getId()) {
        places.add(pos);
      }
      pos++;
    }
    return places;
  }
}
