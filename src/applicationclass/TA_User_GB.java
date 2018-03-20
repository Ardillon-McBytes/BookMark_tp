/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.util.ArrayList;

/**
 * Classe d'association entre les utilisateur et les dossiers de marquepage
 * (Partages et propriétaires)
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_User_GB extends ArrayList<DBTA<User, Groupbook>> {
  // Voir TA_BM_Tag pour voir le fonctionnement
  
  /**
   * 
   * @param id 
   * @param u 
   * @return 
   */
  
  public boolean setUser(int id, User u) {
    for (DBTA<User, Groupbook> ta : this) {
      if (id == ta.getLeft()) {
        ta.setLeft(u);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param id 
   * @param gb 
   * @return 
   */
  public boolean setGroupbook(int id, Groupbook gb) {
    for (DBTA<User, Groupbook> ta : this) {
      if (id == ta.getRight()) {
        ta.setRight(gb);
        return true;
      }
    }
    return false;
  }

  /**
   * 
   * @param gb 
   * @param users 
   * @return 
   */
  public ArrayList<User> getUsers(Groupbook gb, ArrayList<User> users) {
    return Recherche.getUsers(gb, this, users);
  }

  /**
   * 
   * @param u 
   * @param gbs 
   * @return 
   */
  public ArrayList<Groupbook> getGroupbooks(User u, ArrayList<Groupbook> gbs) {
    return Recherche.getGroupbooks(u, this, gbs);
  }
}
