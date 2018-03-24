/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

/**
 * Classe d'association entre les utilisateur et les dossiers de marquepage
 * (Partages et propriétaires)
 * @author Olivier Lemay Dostie
 * @author Jean-Alain Sainton
 * @version 1.0
 */
public class TA_User_GB extends TABase<User, Groupbook> {
  
  public TA_User_GB() {
    TABase.constructor("user_group", 
          1, "id", 
          4, "id_user", 
          2, "id_groupBook");
  }
  
  
}
