/*
 * Cour de la 4e session en Informatique de gestion  (420.AA)
 * Programmation d'environnement graphique           (420-255-SH)
 * Programmation d'environement de base de données   (420-276-SH)
 * TP1 - Remise 2 - Gestionnaire de marquepage
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe d'association entre les marquepage et les tags
 * (Étiquetage des liens)
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_BM_Tag extends ArrayList<DBTA<Bookmark, Tag>> {
  // Ajouter une interfaces pour les groupes de TA?

  /**
   * 
   * @param id 
   * @param bm 
   * @return 
   */
  public boolean setBookmark(int id, Bookmark bm) {
    for (DBTA<Bookmark, Tag> ta : this) {
      // Ou l'inverse? ... if (bm.getId() == ta.getLeft()) {ta.setLeft(id); ...}
      if (id == ta.getLeft()) {
        ta.setLeft(bm);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param id 
   * @param tag 
   * @return 
   */
  public boolean setTag(int id, Tag tag) {
    for (DBTA<Bookmark, Tag> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(tag);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param tag 
   * @param bookmarks 
   * @return 
   */
  // Ne prendre que le premier ou toute la liste?
  //  (voir code en commentaires des deux méthodes suivantes)
  public ArrayList<Bookmark> getBookmark(Tag tag, ArrayList<Bookmark> bookmarks) {
    return Recherche.getBookmarks(tag, this, bookmarks)/*.get(0)*/;
  }

  /**
   * 
   * @param bookmark 
   * @param tags 
   * @return 
   */
  public /*ArrayList<*/Tag/*>*/ getTag(Bookmark bookmark, ArrayList<Tag> tags) {
    return Recherche.getTags(bookmark, this, tags).get(0);
  }
  
}
