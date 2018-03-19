/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

  public boolean setTag(int id, Tag tag) {
    for (DBTA<Bookmark, Tag> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(tag);
        return true;
      }
    }
    return false;
  }

  // Ne prendre que le premier ou toute la liste?
  public ArrayList<Bookmark> getBookmark(Tag tag, ArrayList<Bookmark> bookmarks) {
    return Recherche.getBookmarks(tag, this, bookmarks)/*.get(0)*/;
  }

  public /*ArrayList<*/Tag/*>*/ getTag(Bookmark bookmark, ArrayList<Tag> tags) {
    return Recherche.getTags(bookmark, this, tags).get(0);
  }
  
}
