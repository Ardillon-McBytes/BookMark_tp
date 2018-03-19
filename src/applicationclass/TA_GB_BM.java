/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe d'association entre les marquepages et les dossiers de marquepage
 * (Arborescence)
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_GB_BM extends ArrayList<DBTA<Groupbook, Bookmark>> {
  // Voir TA_BM_Tag pour voir le fonctionnement
  
  /*
  // Méthode sans ArrayList (qui n'utilise pas ListTA_BG)
  public TA_GB_BM(int bookmark, int groupBook) {
    super(bookmark, groupBook);  
  }
  
  public TA_GB_BM(Bookmark bookmark, Groupbook groupBook) {
    super(bookmark, groupBook);  
  }
  */
  
}
