/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclass;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author olivi
 */
public class GestionnaireGB {
  private static Groupbook racine;
  
  public GestionnaireGB(Groupbook racine) {
    GestionnaireGB.racine = racine;
  }
  
  public static ArrayList<Groupbook> 
        getAllGroupbooks() throws Exception {
    if (racine == null) throw new Exception();
    return racine.getGroupBooks();
  }
  
  public static boolean 
        add(Groupbook parent, Groupbook nouveau) throws SQLException, IOException {
    if (racine.equals(nouveau)) throw new IOException();
    return GestionnaireTA.addConteneur(parent, nouveau);
  }
  
  public static boolean 
        add(User acceseur, Groupbook nouveau) throws SQLException, IOException {
    //return GestionnaireTA.addConteneur(parent, nouveau);
    //... en construction
    return false;
  }
  
  public static boolean 
        remove(User accedeur, Groupbook cible) throws SQLException {
    return GestionnaireTA.addAccess(accedeur, cible);
  }
  
  public static DBTA<Groupbook, Groupbook> 
        remove(Groupbook racine, Groupbook cible) throws SQLException {
    return GestionnaireTA.removeConteneur(racine, cible);
  }
  
  public static DBTA<Groupbook, Bookmark> 
        remove(Groupbook dossier, Bookmark bookmark) throws SQLException {
    return GestionnaireTA.removeContenu(dossier, bookmark);
  }
  
//  public static DBTA<Bookmark, Tag> 
//        remove(Bookmark bookmark, Tag tag) throws SQLException {
//    return GestionnaireTA.removeEtiquette(bookmark, tag);
//  }
  
  //public static boolean changerTypeAcces()
  
  
}
