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
  public void test() {
    
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
   * @param user
   * @param shareList
   * @param groupbooks
   * @return
   */
  public static ArrayList<Groupbook> getGroupbooks(
          User user, TA_User_GB shareList, ArrayList<Groupbook> groupbooks) {
    return matchingLeft(user, shareList, groupbooks);
  }
  
  /**
   *
   * @param groupbook
   * @param shareList
   * @param users
   * @return
   */
  public static ArrayList<User> getUsers(
          Groupbook groupbook, TA_User_GB shareList, ArrayList<User> users) {
    return matchingRight(groupbook, shareList, users);
  }
  
  
  // Peut-on formuler une méthode générique pour résuire les répéritions?
  // Voir l'exemple avec la liste de TA_BM_Tag et Tag
  // P-e que l'on devrait implémenter cette méthode dans les TA, 
  //    mais il existe aussi p-e une méthode déjà existante à cause du ArrayList
  // L'ordre des parametre change parfois sinon des signatures se trouvent à être en confli.

  /**
   *
   * @param tas
   * @param gb
   * @return
   */
  public static boolean contains(ArrayList<TA_User_GB> tas, Groupbook gb) {
    return true;
  }
  
  /**
   *
   * @param u
   * @param tas
   * @return
   */
  public static boolean contains(User u, ArrayList<TA_User_GB> tas) {
    return true;
  }
  
  /**
   *
   * @param gb
   * @param tas
   * @return
   */
  public static boolean contains(Groupbook gb, ArrayList<TA_GB_BM> tas) {
    return true;
  }
  
  /**
   *
   * @param bm
   * @param tas
   * @return
   */
  public static boolean contains(Bookmark bm, ArrayList<TA_GB_BM> tas) {
    return true;
  }
  
  /**
   *
   * @param tas
   * @param tag
   * @return
   */
  public static boolean contains(TA_BM_Tag tas, Tag tag) {
    int id = tag.getId();
    return tas.stream().anyMatch((ta) -> (ta.getRight() == id));
  }
  
  /**
   *
   * @param tas
   * @param bm
   * @return
   */
  public static boolean contains(TA_BM_Tag tas, Bookmark bm) {
    int id = bm.getId();
    return tas.stream().anyMatch((ta) -> (ta.getLeft() == id));
  }
}
