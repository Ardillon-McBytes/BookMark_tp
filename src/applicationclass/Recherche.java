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
public class Recherche {
  
  /**
   *
   */
  public static void main(String[] args) {
    
  }
  
  // Façons qui utilisent une méthode générique, mais fonctionne-t-elles?
  private static <FK2 extends DBField, FK1 extends DBField> 
        ArrayList<FK2> matchingLeft(
                FK1 fk1, ArrayList<DBTA<FK1, FK2>> dbta, ArrayList<FK2> list) {
    
    ArrayList<FK2> result = new ArrayList<>();
    //if (dbta.get(0).getLeft() instanceof FK1) {
    dbta.stream().filter((fk) -> (fk.getRight() == 
            fk1.getId())).forEachOrdered((fk) -> {
      result.add((FK2) fk);
    });
    //}
    
    return result;
  }
  
  private static <FK1 extends DBField, FK2 extends DBField> 
        ArrayList<FK1> matchingRight(
                FK2 fk2, ArrayList<DBTA<FK1, FK2>> dbta, ArrayList<FK1> list) {
    
    ArrayList<FK1> result = new ArrayList<>();
    dbta.stream().filter((fk) -> (fk.getLeft() == 
            fk2.getId())).forEachOrdered((fk) -> {
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
    
    for (DBTA<Bookmark, Tag> ta : tagged) {
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
   * @param <TA>
   * @param left
   * @param ta
   * @param rights
   * @return
   */
  public static <L extends DBField, R extends DBField, TA extends TABase<L, R>> 
        ArrayList<R> getRights(L left, TA ta, ArrayList<R> rights) {
    return matchingLeft(left, ta, rights);
  }
  
  /**
   *
   * @param <L>
   * @param <R>
   * @param <TA>
   * @param right
   * @param ta
   * @param lefts
   * @return
   */
  public static <L extends DBField, R extends DBField, TA extends TABase<L, R>> 
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
   * @param <TA>
   * @param ta
   * @param left
   * @param right
   * @return
   */
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>> 
        boolean contains(TA ta, L left, R right) {
    return ta.contains(new DBTA(left, right));
  }
  
  /**
   * 
   * @param <L>
   * @param <R>
   * @param <TA>
   * @param ta
   * @param left
   * @return 
   */
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>>
        boolean containsLeft(TA ta, L left) {
    return ta.stream().anyMatch((d) -> (d.left == left.getId()));
  }
  /*for (DBTA<L, R> d : ta) {
      if (d.left == left.getId()) {
        return true;
      }
    }
    return false;*/
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>> 
        boolean containsRight(TA ta, R right) {
    return ta.stream().anyMatch((d) -> (d.right == right.getId()));
  }
  
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>>
        int getPosition(TA ta, L left, R right) {
     int pos = 0;
     for (DBTA<L,R> t : ta) {
       if (t.equals(new DBTA(left, right))) {
         return pos;
       }
       pos++;
     }
     return pos; // ou -1
  }
  
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>>
        ArrayList<Integer> getLeftPositions(TA ta, L left) {
     ArrayList<Integer> places = new ArrayList<>();
     int pos = 0;
     for (DBTA<L,R> t : ta) {
       if (t.getLeft() == left.getId()) {
         places.add(pos);
       }
       pos++;
     }
     return places;
  }
        
  public static <L extends DBField, R extends DBField, TA extends TABase<L,R>>
        ArrayList<Integer> getRightPositions(TA ta, R right) {
     ArrayList<Integer> places = new ArrayList<>();
     int pos = 0;
     for (DBTA<L,R> t : ta) {
       if (t.getRight() == right.getId()) {
         places.add(pos);
       }
       pos++;
     }
     return places;
  }
}
